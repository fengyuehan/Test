package com.example.plugin;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecSpec;

import java.io.File;

import javax.inject.Inject;

public class JiaguTask extends DefaultTask {
    File apk;
    JiaguExt jiaguExt;

    /**
     * 优先查找@Inject的构造方法
     * @param apk
     * @param jiaguExt
     */
    @Inject
    public JiaguTask(File apk,JiaguExt jiaguExt) {
        this.apk = apk;
        this.jiaguExt = jiaguExt;
        /**
         * 设置分组
         */
        setGroup("jiagu");
    }

    @TaskAction
    public void  a(){
        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                execSpec.commandLine("java","-jar",jiaguExt.getJaiguToolPath(),"-login",
                        jiaguExt.getUserName(),jiaguExt.getUserPwd());

            }
        });

        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                execSpec.commandLine("java","-jar",jiaguExt.getJaiguToolPath(),"-importsign",
                        jiaguExt.getKeyStorePath(),jiaguExt.getKeyStorePass(),
                        jiaguExt.getKeyStoreKeyAlias(),jiaguExt.getKeyStoreKeyAliasPwd());
            }
        });

        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                execSpec.commandLine("java","-jar",jiaguExt.getJaiguToolPath(),"-jiagu",
                        apk.getAbsolutePath(),apk.getParentFile().getAbsolutePath(),"-autosign");
            }
        });
    }
}
