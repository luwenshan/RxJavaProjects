package com.lws.rxjava.demo.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lws.rxjava.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 从磁盘/内存缓存中 获取缓存数据
 * https://www.jianshu.com/p/6f3b6b934787
 */
public class GetCacheActivity extends AppCompatActivity {

    private static final String TAG = "RxJava";

    String memoryCache = null;
    //    String diskCache = "从磁盘缓存中获取数据";
    String diskCache = null;
    @BindView(R.id.text1)
    TextView mText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_text);
        ButterKnife.bind(this);

        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                if (memoryCache != null) {
                    emitter.onNext(memoryCache);
                } else {
                    emitter.onComplete();
                }
            }
        });

        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                if (diskCache != null) {
                    emitter.onNext(diskCache);
                } else {
                    emitter.onComplete();
                }
            }
        });

        Observable<String> network = Observable.just("从网络中获取数据");

        Observable.concat(memory, disk, network)
                .firstElement()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mText1.setText(s);
                    }
                });
    }
}
