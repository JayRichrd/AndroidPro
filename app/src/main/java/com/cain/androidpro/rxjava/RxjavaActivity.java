package com.cain.androidpro.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cain.androidpro.R;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
                    Logger.t(TAG).d("rxJavaSimpleDemo#subscribe: into");
                    emitter.onNext(1);
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.newThread())
                .map(integer -> {
                    Logger.t(TAG).d("rxJavaSimpleDemo#apply: integer = " + integer);
                    return integer + "";
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logger.t(TAG).d("rxJavaSimpleDemo#onSubscribe:");
                    }

                    @Override
                    public void onNext(String s) {
                        Logger.t(TAG).d("rxJavaSimpleDemo#onNext: s = " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Logger.t(TAG).d("rxJavaSimpleDemo#onComplete:");
                    }
                });
    }
}