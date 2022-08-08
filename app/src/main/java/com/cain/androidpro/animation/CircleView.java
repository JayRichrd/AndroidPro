package com.cain.androidpro.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义属性动画的View
 */
public class CircleView extends View {
    private static final String TAG = "CircleView";
    private Circle mCircle;
    // 画笔
    private final Paint mPaint;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mCircle = new Circle(168, Color.RED, 0);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setElevation(mCircle.getElevation());
        mPaint.setColor(mCircle.getColor());
        canvas.drawCircle(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f, mCircle.getRadius(), mPaint);
    }

    /**
     * 自定义属性(circle)的set/get方法
     * 必须实现
     */
    public void setCircle(Circle mCircle) {
        this.mCircle = mCircle;
        // 发起重绘
        postInvalidate();
    }

    public Circle getCircle() {
        return mCircle;
    }
}
