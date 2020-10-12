package com.example.daggerdemo2;

import android.app.Application;

import com.example.daggerdemo2.dagger.di.DaggerHttpComponet;
import com.example.daggerdemo2.dagger.di.HttpComponet;
import com.example.daggerdemo2.dagger.di.HttpModule;

public class App extends Application {

    /**
     * dagger的使用注意事项
     * 1、component的inject方法接受父类型参数，而调入时传入的是子类型对象则无法注入
     * 2、component关联的modules中不能有重复的provide
     * 3、module的peovide方法使用了scope，那么component必须使用同一个注解
     * 4、module的peovide方法没有使用了scope，那么component和module是否加注解都无关紧要
     * 5、component的dependencies与自身的scope不能相同
     * 6、SingleTon的组件不能依赖其他的scope组件，只能其他scope的组件依赖Single的组件
     * 7、没有scope的component不能依赖有scope的component
     * 8、一个component不能同时有多个scope，Subcomponent除外
     * 9、@Singleton的生命周期依附于component。要想保证真正的单例，需要在application中进行初始化
     * 10、Subcomponent同时具备两种不同生命周期的scope，具备父component的，也具备自己的
     * 11、Subcomponent的scope范围小于父Component
     */
    private static HttpComponet httpComponet;

    public static HttpComponet getHttpComponet(){
        return httpComponet;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        httpComponet = DaggerHttpComponet
                .builder()
                .httpModule(new HttpModule())
                .build();
    }
}
