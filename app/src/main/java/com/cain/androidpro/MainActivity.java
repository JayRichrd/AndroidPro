package com.cain.androidpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.cain.androidpro.animation.AnimationActivity;
import com.cain.androidpro.event.EventActivity;
import com.cain.androidpro.rxjava.RxjavaActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

        }
        setContentView(R.layout.activity_main);
        findViewById(R.id.btv_rxjava).setOnClickListener(this);
        findViewById(R.id.btv_event).setOnClickListener(this);
        findViewById(R.id.btv_animation).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btv_rxjava:
                startActivity(new Intent(this, RxjavaActivity.class));
            case R.id.btv_event:
                startActivity(new Intent(this, EventActivity.class));
            case R.id.btv_animation:
                startActivity(new Intent(this, AnimationActivity.class));
            default:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

}