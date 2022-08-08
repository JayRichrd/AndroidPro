package com.cain.androidpro.animation;

/**
 * 自定义属性的对象
 * 用于传递属性
 */
public class Circle {
    // 半径
    private final float radius;
    // 颜色
    private final int color;
    // 高度
    private final int elevation;

    public Circle(float radius, int color, int elevation) {
        this.radius = radius;
        this.color = color;
        this.elevation = elevation;
    }

    public float getRadius() {
        return radius;
    }

    public int getColor() {
        return color;
    }

    public int getElevation() {
        return elevation;
    }
}
