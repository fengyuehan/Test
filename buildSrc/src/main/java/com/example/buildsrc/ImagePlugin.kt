package com.example.buildsrc

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.internal.api.BaseVariantImpl
import com.example.buildsrc.`interface`.IBigImage
import com.example.buildsrc.utils.*
import com.example.buildsrc.webp.WebpUtils
import org.gradle.api.*
import java.io.File
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.collections.ArrayList

class ImagePlugin : Plugin<Project> {

    private lateinit var mcImageProject: Project
    private lateinit var mcImageConfig: Config
    private var oldSize: Long = 0
    private var newSize: Long = 0
    val bigImgList = ArrayList<String>()

    var isDebugTask = false
    var isContainAssembleTask = false

    override fun apply(project: Project) {

        mcImageProject = project

        //check is library or application
        val hasAppPlugin = project.plugins.hasPlugin("com.android.application")
        val variants = if (hasAppPlugin) {
            (project.property("android") as AppExtension).applicationVariants
        } else {
            (project.property("android") as LibraryExtension).libraryVariants
        }
        //set config
        project.extensions.create("McImageConfig", Config::class.java)
        mcImageConfig = project.property("McImageConfig") as Config

        project.gradle.taskGraph.whenReady {
            it.allTasks.forEach { task ->
                val taskName = task.name
                if (taskName.contains("assemble") || taskName.contains("resguard") || taskName.contains("bundle")) {
                    if (taskName.toLowerCase().endsWith("debug") &&
                            taskName.toLowerCase().contains("debug")) {
                        isDebugTask = true
                    }
                    isContainAssembleTask = true
                    return@forEach
                }
            }
        }

        project.afterEvaluate {
            variants.all { variant ->

                variant as BaseVariantImpl

                checkMcTools(project)

                val mergeResourcesTask = variant.mergeResourcesProvider.get()
                val mcPicTask = project.task("McImage${variant.name.capitalize()}")

                mcPicTask.doLast {

                    //debug enable
                    if (isDebugTask && !mcImageConfig.enableWhenDebug) {
                        LogUtil.log("Debug not run ^_^")
                        return@doLast
                    }

                    //assemble passed
                    if (!isContainAssembleTask) {
                        LogUtil.log("Don't contain assemble task, mcimage passed")
                        return@doLast
                    }

                    LogUtil.log("---- McImage Plugin Start ----")
                    LogUtil.log(mcImageConfig.toString())

                    val dir = variant.allRawAndroidResources.files
                    //val dir = getSourcesDirs(project)
                    val cacheList = ArrayList<String>()

                    val imageFileList = ArrayList<File>()

                    for (channelDir: File in dir) {
                        traverseResDir(channelDir, imageFileList, cacheList, object : IBigImage {
                            override fun onBigImage(file: File) {
                                bigImgList.add(file.absolutePath)
                            }
                        })
                    }

                    checkBigImage()

                    val start = System.currentTimeMillis()

                    mtDispatchOptimizeTask(imageFileList)
                    LogUtil.log(sizeInfo())
                    LogUtil.log("---- McImage Plugin End ----, Total Time(ms) : ${System.currentTimeMillis() - start}")
                }

                //chmod task
                val chmodTaskName = "chmod${variant.name.capitalize()}"
                val chmodTask = project.task(chmodTaskName)
                chmodTask.doLast {
                    //chmod if linux
                    if (Tools.isLinux()) {
                        Tools.chmod()
                    }
                }

                //inject task
                (project.tasks.findByName(chmodTask.name) as Task).dependsOn(mergeResourcesTask.taskDependencies.getDependencies(mergeResourcesTask))
                (project.tasks.findByName(mcPicTask.name) as Task).dependsOn(project.tasks.findByName(chmodTask.name) as Task)
                mergeResourcesTask.dependsOn(project.tasks.findByName(mcPicTask.name))

            }
        }

    }

   private fun getSourcesDirs(root:Project):List<File> {
       var dirs = ArrayList<File>()
       root.allprojects{
           project ->
           val hasAppPlugin = project.plugins.hasPlugin("com.android.application")
           if (hasAppPlugin) {
               dirs.addAll(getSourcesDirsWithVariant(((project.property("android") as AppExtension).applicationVariants) as DomainObjectCollection<BaseVariant>))
           } else {
               (project.property("android") as LibraryExtension).libraryVariants
               dirs.addAll(getSourcesDirsWithVariant(((project.property("android") as LibraryExtension).libraryVariants) as DomainObjectCollection<BaseVariant> ))
           }
       }
       return dirs
   }

    private fun getSourcesDirsWithVariant(collection: DomainObjectCollection<BaseVariant>):List<File> {
        var imgDirectories = ArrayList<File>()
        collection.all{
            variant ->
            variant.sourceSets.forEach {
                sourceSet ->
                if (sourceSet.resDirectories.isEmpty()){
                    return@all
                }
                sourceSet.resDirectories.forEach {
                    res->
                    if (res.exists()){
                        if (res.listFiles() == null){
                            return@all
                        }
                        if (res.isDirectory && (res.name.startsWith("drawable") || res.name.startsWith("mipmap"))){
                            if (!imgDirectories.contains(res)){
                                imgDirectories.add(res)
                            }
                        }
                    }
                }

            }
        }

        return imgDirectories
    }

    private fun traverseResDir(file: File, imageFileList: ArrayList<File>, cacheList: ArrayList<String>, iBigImage: IBigImage) {
        if (cacheList.contains(file.absolutePath)) {
            return
        } else {
            cacheList.add(file.absolutePath)
        }
        if (file.isDirectory) {
            file.listFiles()?.forEach {
                if (it.isDirectory) {
                    traverseResDir(it, imageFileList, cacheList, iBigImage)
                } else {
                    filterImage(it, imageFileList, iBigImage)
                }
            }
        } else {
            filterImage(file, imageFileList, iBigImage)
        }
    }

    private fun filterImage(file: File, imageFileList: ArrayList<File>, iBigImage: IBigImage) {
        if (mcImageConfig.whiteList.contains(file.name) || !ImageUtil.isImage(file)) {
            return
        }
        if (((mcImageConfig.isCheckSize && ImageUtil.isBigSizeImage(file, mcImageConfig.maxSize))
                        || (mcImageConfig.isCheckPixels
                        && ImageUtil.isBigPixelImage(file, mcImageConfig.maxWidth, mcImageConfig.maxHeight)))
                && !mcImageConfig.bigImageWhiteList.contains(file.name)) {
            iBigImage.onBigImage(file)
        }
        imageFileList.add(file)
    }

    private fun mtDispatchOptimizeTask(imageFileList: ArrayList<File>) {
        if (imageFileList.size == 0 || bigImgList.isNotEmpty()) {
            return
        }
        val coreNum = Runtime.getRuntime().availableProcessors()
        if (imageFileList.size < coreNum || !mcImageConfig.multiThread) {
            for (file in imageFileList) {
                optimizeImage(file)
            }
        } else {
            val results = ArrayList<Future<Unit>>()
            val pool = Executors.newFixedThreadPool(coreNum)
            val part = imageFileList.size / coreNum
            for (i in 0 until coreNum) {
                val from = i * part
                val to = if (i == coreNum - 1) imageFileList.size - 1 else (i + 1) * part - 1
                results.add(pool.submit(Callable<Unit> {
                    for (index in from..to) {
                        optimizeImage(imageFileList[index])
                    }
                }))
            }
            for (f in results) {
                try {
                    f.get()
                } catch (ignore: Exception) {
                }
            }
        }
    }

    private fun optimizeImage(file: File) {
        val path: String = file.path
        if (File(path).exists()) {
            oldSize += File(path).length()
        }
        when (mcImageConfig.optimizeType) {
            Config.OPTIMIZE_WEBP_CONVERT ->
                WebpUtils.securityFormatWebp(file, mcImageConfig, mcImageProject)
            Config.OPTIMIZE_COMPRESS_PICTURE ->
                CompressUtil.compressImg(file)
        }
        countNewSize(path)
    }

    private fun countNewSize(path: String) {
        if (File(path).exists()) {
            newSize += File(path).length()
        } else {
            //转成了webp
            val indexOfDot = path.lastIndexOf(".")
            val webpPath = path.substring(0, indexOfDot) + ".webp"
            if (File(webpPath).exists()) {
                newSize += File(webpPath).length()
            } else {
                LogUtil.log("McImage: optimizeImage have some Exception!!!")
            }
        }
    }

    private fun checkBigImage() {
        if (bigImgList.size != 0) {
            val stringBuffer = StringBuffer("You have big Imgages with big size or large pixels," +
                    "please confirm whether they are necessary or whether they can to be compressed. " +
                    "If so, you can config them into bigImageWhiteList to fix this Exception!!!\n")
            for (i: Int in 0 until bigImgList.size) {
                stringBuffer.append(bigImgList[i])
                stringBuffer.append("\n")
            }
            throw GradleException(stringBuffer.toString())
        }
    }


    private fun checkMcTools(project: Project) {
        if (mcImageConfig.mctoolsDir.isBlank()) {
            FileUtil.setRootDir(project.rootDir.path)
        } else {
            FileUtil.setRootDir(mcImageConfig.mctoolsDir)
        }

        if (!FileUtil.getToolsDir().exists()) {
            throw GradleException("You need put the mctools dir in project root")
        }
    }

    private fun sizeInfo(): String {
        return "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "before McImage optimize: " + oldSize / 1024 + "KB\n" +
                "after McImage optimize: " + newSize / 1024 + "KB\n" +
                "McImage optimize size: " + (oldSize - newSize) / 1024 + "KB\n" +
                "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-"


    }
}