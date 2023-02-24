package com.cain.androidpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cain.androidpro.animation.AnimationActivity;
import com.cain.androidpro.event.EventActivity;
import com.cain.androidpro.jetpack.JetpackActivity;
import com.cain.androidpro.json.JsonActivity;
import com.cain.androidpro.net.OkHttpActivity;
import com.cain.androidpro.rxjava.RxjavaActivity;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.t(TAG).d("onCreate#");
        if (savedInstanceState != null) {
            Logger.t(TAG).d("onCreate# savedInstanceState = " + savedInstanceState);
        }
        setContentView(R.layout.activity_main);
        findViewById(R.id.btv_rxjava).setOnClickListener(this);
        findViewById(R.id.btv_event).setOnClickListener(this);
        findViewById(R.id.btv_animation).setOnClickListener(this);
        findViewById(R.id.btv_okhttp).setOnClickListener(this);
        findViewById(R.id.btv_jetpack).setOnClickListener(this);
        findViewById(R.id.btn_json).setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.t(TAG).d("onRestart#");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.t(TAG).d("onStart#");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.t(TAG).d("onResume#");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.t(TAG).d("onPause#");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.t(TAG).d("onStop#");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.t(TAG).d("onDestroy#");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btv_rxjava:
                startActivity(new Intent(this, RxjavaActivity.class));
                break;
            case R.id.btv_event:
                startActivity(new Intent(this, EventActivity.class));
                break;
            case R.id.btv_animation:
                startActivity(new Intent(this, AnimationActivity.class));
                break;
            case R.id.btv_okhttp:
                startActivity(new Intent(this, OkHttpActivity.class));
                break;
            case R.id.btv_jetpack:
                startActivity(new Intent(this, JetpackActivity.class));
                break;
            case R.id.btn_json:
                startActivity(new Intent(this, JsonActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.t(TAG).d("onSaveInstanceState#");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.t(TAG).d("onRestoreInstanceState# savedInstanceState = " + savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.t(TAG).d("onNewIntent#");
    }
}