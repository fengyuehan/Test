package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxjava.backpress.BackPressActivity;
import com.example.rxjava.coldOrHotObservable.ColdObservableActivity;
import com.example.rxjava.combine.CombineActivity;
import com.example.rxjava.filter.FilterActivity;
import com.example.rxjava.merge.MerageActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Banner banner;
    private Button mButton,button1,button2,btn_combine,btn_map,btn_flat_map,btn_concat_map,btn_cold_or_hot,btn_merge,btn_fliter;
    private ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);
        banner = findViewById(R.id.banner);
        mButton = findViewById(R.id.btn);
        button1 = findViewById(R.id.btn_background);
        button2 = findViewById(R.id.btn_buffer);
        btn_combine = findViewById(R.id.btn_combine);
        btn_map = findViewById(R.id.btn_map);
        btn_flat_map = findViewById(R.id.btn_flat_map);
        btn_concat_map = findViewById(R.id.btn_concat_map);
        btn_cold_or_hot = findViewById(R.id.btn_cold_or_hot);
        btn_merge = findViewById(R.id.btn_merge);
        btn_fliter = findViewById(R.id.btn_fliter);
        initBanner();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.interval(1,TimeUnit.SECONDS)
                        .takeUntil(new Predicate<Long>() {
                            @Override
                            public boolean test(Long aLong) throws Exception {
                                Log.e("zzf",aLong+"");
                                return aLong >= 10;
                            }
                        })
                        .map(new Function<Long, Long>() {
                            @Override
                            public Long apply(@NonNull Long aLong) throws Exception {

                                return 2 * aLong;
                            }
                        })
                        /*.takeWhile(new Predicate<Long>() {
                            @Override
                            public boolean test(Long aLong) throws Exception {
                                Log.e("zzf",aLong+"");
                                return aLong <= 10;
                            }
                        })*/
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                final Object object = new Object();
                                final int count = (int) (10 - aLong);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        synchronized (object){
                                            textView.setText(count+ "");
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BackPressActivity.class));
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BackgroundActivity.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BufferActivity.class));
            }
        });
        btn_combine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CombineActivity.class));
            }
        });
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map1();
            }
        });
        btn_flat_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 无序,内部是用merge的方式实现，
                 */
                flatMap1();
            }
        });
        btn_concat_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 有序，都是将一个Observableh转化成Observables
                 * 内部采用concat的方式实现
                 *
                 * 与switchMap的区别是，switchMap是当原始的额Observable发射一个新的数据时，它将取消订阅并停止监听之前的那个数据，只监视当前的这个
                 */
                concatMap1();
            }
        });
        btn_cold_or_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ColdObservableActivity.class));
            }
        });
        btn_merge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MerageActivity.class));
            }
        });
        btn_fliter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FilterActivity.class));
            }
        });
    }

    private void concatMap1() {
        Observable<String> createObservable = Observable.just("1", "2", "3", "4", "5", "6", "7", "8", "9");
        Observable<Integer> flatMapObservable = createObservable.concatMap(new Function<String, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(final String s) {
                if (s.equals("2")) {
                    return Observable.create(new ObservableOnSubscribe<Integer>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<Integer> emitter) {
                            emitter.onNext(Integer.valueOf(s) + 1);
                            emitter.onComplete();
                        }
                    }).delay(500, TimeUnit.MILLISECONDS);
                } else {
                    return Observable.create(new ObservableOnSubscribe<Integer>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<Integer> emitter) {
                            emitter.onNext(Integer.valueOf(s) + 1);
                            emitter.onComplete();
                        }
                    });
                }

            }
        });
        Observable<Integer> observeOnObservable = flatMapObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d("zzf", "onSubscribe:" + d.getClass().getName());
            }

            @Override
            public void onNext(@NonNull Integer string) {
                Log.d("zzf", "onNext: " + string);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("zzf", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("zzf", "onComplete");
            }
        };
        observeOnObservable.subscribe(observer);
    }

    private void map1() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        Observable<String> observable1 = observable.map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return String.valueOf(integer + 1);
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d("zzf", "onSubscribe:" + d.getClass().getName());
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d("zzf", "onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("zzf", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("zzf", "onComplete");
            }
        };
        observable1.subscribe(observer);
    }

    private void flatMap1() {
        Observable<String> createObservable = Observable.just("1", "2", "3", "4", "5", "6", "7", "8", "9");
        Observable<Integer> flatMapObservable = createObservable.flatMap(new Function<String, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(final String s) {
                if (s.equals("2")) {
                    return Observable.create(new ObservableOnSubscribe<Integer>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<Integer> emitter) {
                            emitter.onNext(Integer.valueOf(s) + 1);
                            emitter.onComplete();
                        }
                    }).delay(500, TimeUnit.MILLISECONDS);
                } else {
                    return Observable.create(new ObservableOnSubscribe<Integer>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<Integer> emitter) {
                            emitter.onNext(Integer.valueOf(s) + 1);
                            emitter.onComplete();
                        }
                    });
                }

            }
        });
        Observable<Integer> observeOnObservable = flatMapObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d("zzf", "onSubscribe:" + d.getClass().getName());
            }

            @Override
            public void onNext(@NonNull Integer string) {
                Log.d("zzf", "onNext: " + string);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("zzf", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("zzf", "onComplete");
            }
        };
        observeOnObservable.subscribe(observer);
    }

    private void map() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        Observable<String> observable1 = observable.map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return String.valueOf(integer + 1);
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d("zzf", "onSubscribe:" + d.getClass().getName());
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d("zzf", "onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("zzf", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("zzf", "onComplete");
            }
        };
        observable1.subscribe(observer);
    }

    private void initBanner() {
        List<String> mImageList = new ArrayList<>();
        List<String> mTitleList = new ArrayList<>();
        mImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        mImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        mImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        mImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        mTitleList.add("好好学习");
        mTitleList.add("天天向上");
        mTitleList.add("热爱劳动");
        mTitleList.add("不搞对象");

        //设置样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(mImageList);
        //设置动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合
        banner.setBannerTitles(mTitleList);
        //设置自动轮播
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                /*Intent intent = new Intent(getActivity(),X5WebViewActivity.class);
                intent.putExtra("mUrl",mUrlList.get(position));
                intent.putExtra("mTitle",mTitleList.get(position));
                startActivity(intent);*/

            }
        });
        banner.start();
    }
}
