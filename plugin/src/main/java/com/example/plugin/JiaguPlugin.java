package com.example.plugin;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariantOutput;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

import groovy.util.logging.Log;


public class JiaguPlugin implements Plugin<Project> {
    /**
     * jiagu{
     *     userName "xxx"
     *     userPwd "xxx"
     *     keyStorePath "xxx"
     *     keyStorePass "xxx"
     *     keyStoreKeyAlias "xxx"
     *     keyStoreKeyAliasPwd "xxx"
     *     jaiguToolPath "xxx"
     * }
     * @param project
     */
    @Override
    public void apply(Project project) {

         // create方法里面的第一个参数就对应上面的开头，
         //class文件里面的字段对应上面的需要填写的内容
        final JiaguExt jiaguExt = project.getExtensions().create("jiagu",JiaguExt.class);
        //这个方法表示先要解析build.gradle，解析完成之后才能拿到对应的数据
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(final Project project) {
                String name = jiaguExt.getUserName();
                System.out.println(name);
                //能够动态的获取当前（debug/release）的apk文件
                AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
                //得到debug/release目录
                appExtension.getApplicationVariants().all(new Action<ApplicationVariant>() {
                    @Override
                    public void execute(ApplicationVariant applicationVariant) {
                        final String name = applicationVariant.getName();
                        applicationVariant.getOutputs().all(new Action<BaseVariantOutput>() {
                            @Override
                            public void execute(BaseVariantOutput baseVariantOutput) {
                                File outputFile =  baseVariantOutput.getOutputFile();
                                /**
                                 * 创建一个Task，会在右边的gradle界面生成一个Task
                                 */
                                project.getTasks().create("jiagu" + name,JiaguTask.class,outputFile,jiaguExt);
                            }
                        });
                    }
                });
            }
        });
    }
}
