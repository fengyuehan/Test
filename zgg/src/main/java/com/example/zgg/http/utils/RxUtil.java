package com.example.zgg.http.utils;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {
    /*
     * 线程调度
     * ① Schedulers.trampoline():   在当前线程立即执行任务，如果当前线程有任务在执行，则会将其暂停，等插入进来的任务执行完之后，再将未完成的任务接着执行。
     * ② Schedulers.newThread():    在每执行一个任务时创建一个新的线程，不具有线程缓存机制，因为创建一个新的线程比复用一个线程更耗时耗力，虽然使用Schedulers.io( )的地方，
     *                               都可以使用Schedulers.newThread( )，但是，Schedulers.newThread( )的效率没有Schedulers.io( )高。
     * ③ Schedulers.io():           I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，
     *                               区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
     * ④ Schedulers.computation():  计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。
     *                               这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     * ⑤ AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
     * ⑥ Schedulers.single():        拥有一个线程单例，所有的任务都在这一个线程中执行，当此线程中有任务执行时，其他任务将会按照先进先出的顺序依次执行。
     *
     */


    /**
     * 通用切换线程
     *
     * @param subscribeOn
     * @param observeOn
     * @param delayError
     * @param <T>
     * @return
     */


    private static <T> ObservableTransformer<T, T> schedulerTransformer(final Scheduler subscribeOn, final Scheduler observeOn, final boolean delayError) {
        return observable -> observable.subscribeOn(subscribeOn).observeOn(observeOn, delayError);
    }

    public static <T> ObservableTransformer<T, T> io2main2() {
        return schedulerTransformer(Schedulers.io(), AndroidSchedulers.mainThread(), false);
    }

    private static <T> ObservableTransformer<T, T> io2main(final Scheduler subscribeOn, final Scheduler observeOn, final boolean delayError) {
         return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /*
     * 监听Activity、Fragment的生命周期，来自动断开subscription以防止内存泄漏
     * 也就是当页面离开时，自动断开网络请求数据的处理过程，即数据返回后不再进行任何处理
     */

    /**
     * activity
     *
     * @param activity
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> lifecycle(RxAppCompatActivity activity) {
        return observable -> observable.compose(activity.bindUntilEvent(ActivityEvent.DESTROY));
    }

    /**
     * fragment
     *
     * @param fragment
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> lifecycle(RxFragment fragment) {
        return observable -> observable.compose(fragment.bindUntilEvent(FragmentEvent.DESTROY));
    }


    public static void io(Action action) {
        Observable.create(e -> action.run()).subscribeOn(Schedulers.io()).subscribe();
    }

    public static void delay(Action action, int time) {
        Observable.create(e -> e.onNext("")).delay(time, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(o -> action.run());
    }
}
