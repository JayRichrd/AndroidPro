package com.cain.androidpro.animation;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.cain.androidpro.R;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleView mCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        findViewById(R.id.btn_animation_start).setOnClickListener(this);
        mCircleView = findViewById(R.id.cv_custom);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_animation_start:
                startAnimation();
                break;
            default:
                break;
        }
    }

    private void startAnimation() {
        Circle startCircle = new Circle(168, Color.RED, 0);
        Circle middleCircle = new Circle(300, Color.GREEN, 15);
        Circle endCircle = new Circle(450, Color.BLUE, 30);
        ObjectAnimator animator = ObjectAnimator.ofObject(mCircleView, "circle", new CircleEvaluator(), startCircle, middleCircle, endCircle)
                .setDuration(5000);
        // 显式设置一个插值器
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }
}