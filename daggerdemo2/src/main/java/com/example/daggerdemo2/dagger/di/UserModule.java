package com.example.daggerdemo2.dagger.di;


import android.content.Context;

import com.example.daggerdemo2.dagger.ApiService;
import com.example.daggerdemo2.dagger.HttpService;
import com.example.daggerdemo2.dagger.UserManager;
import com.example.daggerdemo2.dagger.UserStore;
import com.example.daggerdemo2.dagger.scope.AppScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * 1、@Module(includes = {HttpModule.class})
 * 因为UserModule使用到了HttpModule里面的OkHttpClient对象，所以需要下面的方法进行关联获取其OkHttpClient对象
 * 2、@Component(modules = {UserModule.class,HttpModule.class})
 * 这时需要修改MainActivity里面Component的初始化
 * DaggerUserComponet.builder()
 *                 .userModule(new UserModule(this))
 *                 .httpModule(new HttpModule())//多加了这一句
 *                 .build()
 *                 .inject(this);
 * 3、添加一个Component,
 * @Singleton
 * @Component(modules = {HttpModule.class})
 * public interface HttpComponet {
 *
 * }
 * //@Singleton
 * @Component(modules = {UserModule.class},dependencies={HttpComponet.class})
 *
 * public interface UserComponet {
 *     void inject(MainActivity mainActivity);
 * }
 * 此时UserComponet就不需要添加@Singleton注解，只需要在HttpComponet上面添加。
 * okHttpClient在HttpModule中是单例的，所以HttpComponet中必须添加@Singleton
 * 但是UserComponet是依赖HttpComponet的，我们不加注解就会报错。
 *
 * error:UserComponet (unscoped) cannot depend on scoped components
 * 意思没有 unscoped的不能依赖有scope的components，所以我们也给UserComponet添加上@Singleton注解
 *
 * error:This @Singleton component cannot depend on scoped components
 *
 * 意思是@Singleton不能作用在不同的component上，也就是我们需要保证两个component的csope不同，所以我们需要自定义scope
 *
 * 解决方法：
 *
 * @Scope
 * @Documented
 * @Retention(RUNTIME)
 * public @interface AppScope {
 * }
 *
 * 然后在UserComponet加上即可。
 *
 * 为了保证是在Http全局单例，HttpComponet初始化在application里面初始化
 *  public class App extends Application {
 *
 *     private static HttpComponet httpComponet;
 *
 *     public static HttpComponet getHttpComponet(){
 *         return httpComponet;
 *     }
 *
 *     @Override
 *     public void onCreate() {
 *         super.onCreate();
 *         httpComponet = DaggerHttpComponet
 *                 .builder()
 *                 .httpModule(new HttpModule())
 *                 .build();
 *     }
 * }
 *
 */
@Module/*(includes = {HttpModule.class})*/
public class UserModule {

    /**
     * 我们需要的Context，其实也可以创建一个application级别的module
     */
    private Context mContext;

    public UserModule(Context context){
        mContext = context;
    }

    @Provides
    public HttpService getHttpService(){
        return new HttpService();
    }

    /**
     * 在module提供的方法，必须为public，不然在build的时候会报错
     * @return
     */

    /**
     * error:Cannot have more than one binding method with the same name in a single module
     * 意思在同一个single module不能出现两个相同名字的方法
     *
     * 改成getApiService2后有报错
     * error:ApiService is bound multiple times
     * 意思是ApiService多次绑定，表示同一个类虽然可以有不同的狗仔函数，但是只能绑定一次
     *
     */
    /*@Provides
    public ApiService getApiService2(){
        return new ApiService();
    }*/

    @Provides
    public ApiService getApiService(OkHttpClient okHttpClient,String url){
        return new ApiService(okHttpClient,url);
    }

    /*@Provides
    public UserStore getUserStore(){
        return new UserStore();
    }
*/

    /**
     * 我们知道一般OkHttpClient都是以单例的形式去实现，如果按照这种方法实现的话
     * 会导致每次调用OkHttpClient进行网络请求的似乎都会重新创建一个新的对象
     * 这样浪费资源，所以需要曹勇其他的方法，重新创建一个HttpModule。
     * @return
     */
    /*@Provides
    public OkHttpClient getOkHttpClient(){
        return new OkHttpClient().newBuilder().build();
    }*/

    /*@AppScope*/
    @Provides
    public String url(){
        return "Http://baidu.com";
    }

    /**
     * com.example.daggerdemo2.dagger.di.UserComponet (unscoped) may not reference scoped bindings:
     *
     * @param okHttpClient
     * @param url
     * @return
     */




    @Provides
    public UserStore getUserStore(){
        /**
         * 此时需要一个上下文，需要从module的构造函数进行传递
         */
        return new UserStore(mContext);
    }

    /**
     * com.example.daggerdemo2.dagger.UserManager cannot be provided without an @Inject constructor or an @Provides-annotated method.
     * public interface UserComponet
     * 意思是没有提供ApiService的构造函数。
     * 有两种方法提供
     * 1、@Provides
     *     public ApiService getApiService(){
     *         return new ApiService();
     *     }
     *
     * 2、直接在ApiService的构造函数上加个注解
     *
     *     @Inject
     *     public ApiService(){
     *
     *     }
     * @param apiService
     * @return
     */

    @Provides
    public UserManager getUserManager(ApiService apiService,UserStore userStore){
        return new UserManager(apiService,userStore);
    }
}
