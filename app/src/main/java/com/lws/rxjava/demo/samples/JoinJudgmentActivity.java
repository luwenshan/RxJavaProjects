package com.lws.rxjava.demo.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lws.rxjava.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * 联合判断多个事件
 * https://www.jianshu.com/p/2becc0eaedab
 */
public class JoinJudgmentActivity extends AppCompatActivity {

    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.age)
    EditText mAge;
    @BindView(R.id.job)
    EditText mJob;
    @BindView(R.id.list)
    Button mList;

    CompositeDisposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_judgment);
        ButterKnife.bind(this);
        mList.setEnabled(false);

        /*
         * 为每个EditText设置被观察者，用于发送监听事件
         * 说明：
         * 1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher）
         * 2. 传入EditText控件，点击任1个EditText撰写时，都会发送数据事件 = Function3（）的返回值
         * 3. 采用skip(1)原因：跳过 一开始EditText无任何输入时的空值
         **/
        Observable<CharSequence> nameObservable = RxTextView.textChanges(mName).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(mAge).skip(1);
        Observable<CharSequence> jobObservable = RxTextView.textChanges(mJob).skip(1);

        Observable.combineLatest(nameObservable, ageObservable, jobObservable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2, @NonNull CharSequence charSequence3) throws Exception {
                boolean isUserNameValid = !TextUtils.isEmpty(mName.getText());
                boolean isUserAgeValid = !TextUtils.isEmpty(mAge.getText());
                boolean isUserJobValid = !TextUtils.isEmpty(mJob.getText());

                return isUserNameValid && isUserAgeValid && isUserJobValid;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                mList.setEnabled(aBoolean);
            }
        });
    }
}
