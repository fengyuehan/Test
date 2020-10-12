package com.example.daggerdemo2.dagger.di;

import com.example.daggerdemo2.MainActivity;
import com.example.daggerdemo2.dagger.scope.AppScope;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component是连接module和inject的中间人
 * @Component(modules = {UserModule.class})表示关联到module
 * void inject(MainActivity mainActivity);表示与activity联系起来，因为在activity里需要注入对象
 */

/**
 * error:com.example.daggerdemo2.dagger.di.UserComponet (unscoped) may not reference scoped bindings:
 * 解决方法：
 * module里面是@Singleton，则对应的Component也必须是这个scope注解，不然会报错
 */

/**
 * error : UserComponet (unscoped) cannot depend on scoped components
 *
 */
//@Singleton
@AppScope
@Component(modules = {UserModule.class},dependencies = {HttpComponet.class})
public interface UserComponet {
    void inject(MainActivity mainActivity);
}
