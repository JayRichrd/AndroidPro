package com.cain.androidpro.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cain.androidpro.R;

import io.reactivex.Observable;

public class RxjavaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "RxjavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        findViewById(R.id.btv_rxjava_demo).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btv_rxjava_demo:
                rxJavaSimpleDemo();
                break;
            default:
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void rxJavaSimpleDemo() {
        Observable.just("hello rxjava").subscribe(s -> Log.i(TAG, "accept: str = " + s));
    }
}