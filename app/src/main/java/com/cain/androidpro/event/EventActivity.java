package com.cain.androidpro.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.cain.androidpro.MainActivity;
import com.cain.androidpro.R;
import com.cain.androidpro.net.OkHttpActivity;

public class EventActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    public static final String TAG = "EventDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        //        findViewById(R.id.btv_event_demo).setOnClickListener(this);
        findViewById(R.id.btv_event_demo).setOnTouchListener(this);
        findViewById(R.id.tv_event_demo).setOnTouchListener(this);
        findViewById(R.id.tv_event_demo).setOnClickListener(this);
        findViewById(R.id.btv_start_main).setOnClickListener(this);
        //        findViewById(R.id.cl_root).setOnTouchListener(this);
        //        findViewById(R.id.cl_root).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btv_event_demo:
                Log.d(TAG, "EventActivity#onClick: event demo btn is clicked.");
                break;
            case R.id.btv_start_main:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.cl_root:
                Log.d(TAG, "EventActivity#onClick: event demo cl root is clicked.");
                break;
            case R.id.tv_event_demo:
                Log.d(TAG, "EventActivity#onClick: event demo text is clicked.");
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "EventActivity#dispatchTouchEvent: action = " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "EventActivity#onTouchEvent: action = " + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "EventActivity#onTouch: v = " + v.toString() + ", event action = " + event.getAction());
        return false;
    }
}