package com.lws.rxjava.demo.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.lws.rxjava.demo.R;
import com.lws.rxjava.demo.bean.Translation;
import com.lws.rxjava.demo.net.IGetRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

        testMerge();
        testZip();
    }

    private void testZip() {
        Observable<Translation> observable1, observable2;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        IGetRequest request = retrofit.create(IGetRequest.class);
        observable1 = request.getCall1().subscribeOn(Schedulers.io());
        observable2 = request.getCall2().subscribeOn(Schedulers.io());
        Observable.zip(observable1, observable2, new BiFunction<Translation, Translation, String>() {
            @Override
            public String apply(@NonNull Translation translation1, @NonNull Translation translation2) throws Exception {
                return translation1.getContent().getOut() + " & " + translation2.getContent().getOut();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mText2.setText(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mText2.setText("登陆失败");
                    }
                });
    }

    private void testMerge() {
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
