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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_judgment);
        ButterKnife.bind(this);
        mList.setEnabled(false);

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
