package com.cain.androidpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cain.androidpro.animation.AnimationActivity;
import com.cain.androidpro.event.EventActivity;
import com.cain.androidpro.net.OkHttpActivity;
import com.cain.androidpro.rxjava.RxjavaActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btv_rxjava).setOnClickListener(this);
        findViewById(R.id.btv_event).setOnClickListener(this);
        findViewById(R.id.btv_animation).setOnClickListener(this);
        findViewById(R.id.btv_okhttp).setOnClickListener(this);
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
            default:
                break;
        }
    }
}