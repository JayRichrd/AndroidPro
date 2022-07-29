package com.cain.androidpro.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomConstraintLayout extends ConstraintLayout {
    public CustomConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(EventActivity.TAG, "CustomConstraintLayout#onInterceptTouchEvent: event action = " + ev.getAction());
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            return true;
//        }
        return super.onInterceptTouchEvent(ev);
    }
}
