package com.lws.rxjava.demo.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.lws.rxjava.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 合并数据源 & 同时展示
 * https://www.jianshu.com/p/fc2e551b907c
 */
public class MergeDataSourceActivity extends AppCompatActivity {

    private static final String TAG = "RxJava";

    @BindView(R.id.text1)
    TextView mText1;
    @BindView(R.id.text2)
    TextView mText2;
    String result = "数据源来自 = ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_text);
        ButterKnife.bind(this);


        Observable<String> network = Observable.just("网络");
        Observable<String> file = Observable.just("本地文件");
        Observable.merge(network, file)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        result += s + "+";
                        Log.d(TAG, "数据源有： " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "获取数据完成");
                        mText1.setText(result);
                    }
                });
    }
}
