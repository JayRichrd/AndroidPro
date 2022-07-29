package com.cain.androidpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cain.androidpro.event.EventActivity;
import com.cain.androidpro.rxjava.RxjavaActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btv_rxjava).setOnClickListener(this);
        findViewById(R.id.btv_event).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btv_rxjava:
                startActivity(new Intent(this, RxjavaActivity.class));
            case R.id.btv_event:
                startActivity(new Intent(this, EventActivity.class));
            default:
                break;
        }
    }
}