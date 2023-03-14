package com.cain.androidpro.json;

import androidx.annotation.NonNull;

public class ChildTest extends ParentTest {
    String url;

    @NonNull
    @Override
    public String toString() {
        return "ChildTest{" +
                "url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
