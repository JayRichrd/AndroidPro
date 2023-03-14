package com.cain.androidpro.json;

import androidx.annotation.NonNull;

public class ParentChildTest {
    private transient int sites;
    public static String TAG = "ParentChildTest";
    private ParentTest obj;

    @NonNull
    @Override
    public String toString() {
        return "ParentChildTest{" +
                "sites=" + sites +
                ", obj=" + obj +
                '}';
    }
}
