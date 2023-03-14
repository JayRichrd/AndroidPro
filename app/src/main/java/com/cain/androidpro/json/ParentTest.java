package com.cain.androidpro.json;

import androidx.annotation.NonNull;

import java.security.PublicKey;

public class ParentTest {
    protected String id;
    protected String name;

    @NonNull
    @Override
    public String toString() {
        return "ParentTest{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
