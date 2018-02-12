package com.lws.rxjava.demo.samples;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lws.rxjava.demo.R;
import com.lws.rxjava.demo.bean.Translation;
import com.lws.rxjava.demo.net.IGetRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求嵌套回调
 * https://www.jianshu.com/p/5f5d61f04f96
 */
@SuppressLint("SetTextI18n")
public class NestedCallbackActivity extends AppCompatActivity {

    private static final String TAG = "RxJava";

    Observable<Translation> mObservableRegister;
    Observable<Translation> mObservableLogin;

    @BindView(R.id.text1)
    TextView mText1;
    @BindView(R.id.text2)
    TextView mText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_callback);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IGetRequest request = retrofit.create(IGetRequest.class);

        mObservableRegister = request.register();
        mObservableLogin = request.login();

        mObservableRegister.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        //主线程
                        mText1.setText("第1次网络请求成功: " + translation.getContent().getOut());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Translation, ObservableSource<Translation>>() {
                    @Override
                    public ObservableSource<Translation> apply(@NonNull Translation translation) throws Exception {
                        return mObservableLogin;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        mText2.setText("第2次网络请求成功: " + translation.getContent().getOut());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mText2.setText("登陆失败");
                    }
                });
    }
}
