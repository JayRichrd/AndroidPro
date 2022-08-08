package com.cain.androidpro.animation;

import android.animation.TypeEvaluator;

/**
 * 自定义属性-估值器
 */
public class CircleEvaluator implements TypeEvaluator<Circle> {
    private static final String TAG = "CircleEvaluator";

    @Override
    public Circle evaluate(float fraction, Circle startValue, Circle endValue) {
        /*
         * 根据fraction和初始值/结束值计算此时应该返回对象Circle
         */
        float radius = startValue.getRadius() + fraction * (endValue.getRadius() - startValue.getRadius());
        int color = (int) (startValue.getColor() + fraction * (endValue.getColor() - startValue.getColor()));
        int evaluate = (int) (startValue.getElevation() + fraction * (endValue.getElevation() - startValue.getElevation()));
        return new Circle(radius, color, evaluate);
    }
}
