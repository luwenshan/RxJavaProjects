package com.lws.rxjava.demo.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lws.rxjava.demo.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 联想搜索优化
 * https://www.jianshu.com/p/ba0e7df9b927
 */
public class AssociativeSearchActivity extends AppCompatActivity {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_search_result)
    TextView mTvSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associative_search);
        ButterKnife.bind(this);

        RxTextView.textChanges(mEtSearch)
                .debounce(1, TimeUnit.SECONDS)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CharSequence charSequence) {
                        mTvSearchResult.setText("Result: " + charSequence.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
