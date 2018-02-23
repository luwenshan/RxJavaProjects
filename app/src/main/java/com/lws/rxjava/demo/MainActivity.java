package com.lws.rxjava.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lws.rxjava.demo.samples.AntiShakeActivity;
import com.lws.rxjava.demo.samples.AssociativeSearchActivity;
import com.lws.rxjava.demo.samples.GetCacheActivity;
import com.lws.rxjava.demo.samples.JoinJudgmentActivity;
import com.lws.rxjava.demo.samples.MergeDataSourceActivity;
import com.lws.rxjava.demo.samples.NestedCallbackActivity;
import com.lws.rxjava.demo.samples.Polling1Activity;
import com.lws.rxjava.demo.samples.Polling2Activity;
import com.lws.rxjava.demo.samples.ReconnectActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(MainActivity.this, Polling1Activity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(MainActivity.this, Polling2Activity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(MainActivity.this, ReconnectActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(MainActivity.this, NestedCallbackActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(MainActivity.this, GetCacheActivity.class));
                break;
            case R.id.btn6:
                startActivity(new Intent(MainActivity.this, MergeDataSourceActivity.class));
                break;
            case R.id.btn7:
                startActivity(new Intent(MainActivity.this, JoinJudgmentActivity.class));
                break;
            case R.id.btn8:
                startActivity(new Intent(MainActivity.this, AntiShakeActivity.class));
                break;
            case R.id.btn9:
                startActivity(new Intent(MainActivity.this, AssociativeSearchActivity.class));
                break;
        }
    }
}
