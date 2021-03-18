package com.example.androidplugin;

import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.zip.ZipFile;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * author : zhangzf
 * date   : 2021/3/17
 * desc   :
 */
public class BaseDexClassLoaderHookHelper {
    /*
     * 默认情况下performLaunchActivity会使用替身StubActivity的ApplicationInfo也就是宿主程序的ClassLoader加载所有的类;
     * 我们的思路是告诉宿主ClassLoader我们在哪,让其帮助完成类加载的过程.
     * <p>
     * 宿主程序的ClassLoader最终继承自BaseDexClassLoader,BaseDexClassLoader通过DexPathList进行类的查找过程;
     * 而这个查找通过遍历一个dexElements的数组完成;
     * <p>
     * 我们通过把插件dex添加进这个数组就让宿主ClassLoader获取了加载插件类的能力.
     * <p>
     * 系统使用ClassLoader findClass的过程,发现应用程序使用的非系统类都是通过同一个PathClassLoader加载的;
     * 而这个类的最终父类BaseDexClassLoader通过DexPathList完成类的查找过程;我们hack了这个查找过程,从而完成了插件类的加载
     */

    /**
     * 使用宿主ClassLoader帮助加载插件类
     * java.lang.IllegalAccessError: Class ref in pre-verified class resolved to unexpected implementation
     * 在插件apk和宿主中包含了相同的Jar包;解决方法,插件编译时使用compileOnly依赖和宿主相同的依赖.
     * https://blog.csdn.net/berber78/article/details/41721877
     * @param classLoader
     * @param dexFile
     * @param optDexFile
     */
    public static void patchClassLoader(ClassLoader classLoader, File dexFile, File optDexFile) {
        // -->PathClassLoader
        // -->BaseDexClassLoader
        // -->BaseDexClassLoader中DexPathList pathList
        // -->DexPathList中 Element[] dexElements

        try {
            //0.获取PathClassLoader的父类dalvik.system.BaseDexClassLoader的Class对象
            Class<?> baseDexClassLoaderClazz = PathClassLoader.class.getSuperclass();
            if (baseDexClassLoaderClazz == null) return;
            //1.获取BaseDexClassLoader的成员DexPathList pathList
            //private final DexPathList pathList;
            //http://androidxref.com/9.0.0_r3/xref/libcore/dalvik/src/main/java/dalvik/system/BaseDexClassLoader.java
            Field pathListField = baseDexClassLoaderClazz.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            //2.获取DexPathList pathList实例;
            Object dexPathList = pathListField.get(classLoader);
            if (dexPathList == null){
                return;
            }
            //3.获取DexPathList的成员: Element[] dexElements 的Field
            //private Element[] dexElements;
            Field dexElementsField = dexPathList.getClass().getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            //4.获取DexPathList的成员 Element[] dexElements 的值
            //Element是DexPathList的内部类
            Object[] dexElements = (Object[]) dexElementsField.get(dexPathList);
            if (dexElements == null) return;
            //5.获取dexElements数组的类型 (Element)
            // 数组的 class 对象的getComponentType()方法可以取得一个数组的Class对象,即得到这个数组的类型为Element.class
            Class<?> elementClazz = dexElements.getClass().getComponentType();
            if (elementClazz == null) return;

            //6.创建一个数组, 用来替换原始的数组
            //通过Array.newInstance()可以反射生成数组对象,生成数组,指定元素类型和数组长度
            Object[] hostAndPluginElements = (Object[]) Array.newInstance(elementClazz, dexElements.length + 1);

            //根据不同的API, 获取插件DexClassLoader的 DexPathList中的 dexElements数组
            Object elementPluginObj;
            if (Build.VERSION.SDK_INT >= 26){
                Constructor<?> elementConstructor = elementClazz.getDeclaredConstructor(DexFile.class, File.class);
                elementConstructor.setAccessible(true);
                @SuppressWarnings("deprecation")
                DexFile dexFile1 = DexFile.loadDex(dexFile.getCanonicalPath(),optDexFile.getCanonicalPath(),0);
                elementPluginObj = elementConstructor.newInstance(dexFile1,dexFile);
            }else if (Build.VERSION.SDK_INT >= 18) {
                //18<=API<=25 (4.3<=API<=7.1.1)
                //7.构造插件Element
                // 使用构造函数 public Element(File file, boolean isDirectory, File zip, DexFile dexFile){}
                Constructor<?> elementConstructor = elementClazz.getDeclaredConstructor(File.class, boolean.class, File.class, DexFile.class);
                elementConstructor.setAccessible(true);

                //8. 生成Element的实例对象
                DexFile dexFile2 = DexFile.loadDex(dexFile.getCanonicalPath(), optDexFile.getCanonicalPath(), 0);
                elementPluginObj = elementConstructor.newInstance(dexFile, false, dexFile, dexFile2);
            } else if (Build.VERSION.SDK_INT == 17) {
                //API=17  (API=4.2)
                //7.构造插件Element
                // 使用构造函数:public Element(File file, File zip, DexFile dexFile){}
                Constructor<?> elementConstructor = elementClazz.getDeclaredConstructor(File.class, File.class, DexFile.class);
                elementConstructor.setAccessible(true);

                //8. 生成Element的实例对象
                DexFile dexFile3 = DexFile.loadDex(dexFile.getCanonicalPath(), optDexFile.getCanonicalPath(), 0);
                elementPluginObj = elementConstructor.newInstance(dexFile, dexFile, dexFile3);
            } else {
                //15~16 (4.0.3=<API=4.1)
                //7.构造插件Element
                // 使用构造函数:public Element(File file, ZipFile zipFile, DexFile dexFile){}
                Constructor<?> elementConstructor = elementClazz.getDeclaredConstructor(File.class, ZipFile.class, DexFile.class);
                elementConstructor.setAccessible(true);

                //8. 生成Element的实例对象
                DexFile dexFile4 = DexFile.loadDex(dexFile.getCanonicalPath(), optDexFile.getCanonicalPath(), 0);
                elementPluginObj = elementConstructor.newInstance(dexFile, new ZipFile(dexFile), dexFile4);
            }
            //9.创建插件element数组
            Object[] pluginElements = new Object[]{elementPluginObj};
            //10.把插件的element数组复制进去
            System.arraycopy(pluginElements, 0, hostAndPluginElements, 0, pluginElements.length);

            //11.把宿主的elements复制进去
            System.arraycopy(dexElements, 0, hostAndPluginElements, pluginElements.length, dexElements.length);

            //12.替换
            dexElementsField.set(dexPathList, hostAndPluginElements);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
