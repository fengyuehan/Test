package com.example.rxjava;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchActivity extends AppCompatActivity {
    private EditText mEditText;
    private TextView mTextView;
    private PublishSubject<String> mPublishSubject;
    private DisposableObserver<String> mDisposableObserver;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mEditText = findViewById(R.id.et);
        mTextView = findViewById(R.id.tv);
        initView();
    }

    private void initView() {
        mPublishSubject = PublishSubject.create();
        mCompositeDisposable = new CompositeDisposable();
        mDisposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                mTextView.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });
        //debounce:发送一个延时消息给下游，如果在这段延时时间内没有收到新的请求，那么下游就会收到该消息；而如果在这段延时时间内收到来新的请求，
        // 那么就会取消之前的消息，并重新发送一个新的延时消息
        //switchMap:当源Observable发射一个新的数据项时，如果旧数据项订阅还未完成，就取消旧订阅数据和
        // 停止监视那个数据项产生的Observable，开始监视新的数据项。如果都是在同一个线程里跑的话，
        // 那么该操作符与ContactMap无异；只有在不同的线程里跑的时候，即线程方案为newThread的时候，才会出现这种情况。

        mPublishSubject
                .debounce(200, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.length() > 0;
                    }
                })
                .switchMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                return getSearchObservable(s);
            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe((Consumer<? super Object>) mDisposableObserver);
        mCompositeDisposable.add(mDisposableObserver);
    }

    private void search(String s) {
        mPublishSubject.onNext(s);
    }

    private ObservableSource<?> getSearchObservable(final String s) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d("SearchActivity", "开始请求，关键词为：" + s);
                try {
                    Thread.sleep(100 + (long) (Math.random() * 500));
                } catch (InterruptedException e) {
                    if (!emitter.isDisposed()) {
                        emitter.onError(e);
                    }
                }
                Log.d("SearchActivity", "结束请求，关键词为：" + s);
                emitter.onNext("完成搜索，关键词为：" + s);
                emitter.onComplete();
            }
        })
                //上游的线程用subscribeOn
                .subscribeOn(Schedulers.io());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
