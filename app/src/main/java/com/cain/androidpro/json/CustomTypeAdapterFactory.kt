package com.cain.androidpro.json

import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken

class CustomTypeAdapterFactory : TypeAdapterFactory {
    companion object {
        private const val TAG = "CustomTypeAdapterFactory"
    }

    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val raw = type.rawType
        val generalType = type.type
        Log.i(TAG, "create: raw = $raw, generalType = $generalType")
        if (JsonItem::class.java.isAssignableFrom(raw)){
            return JsonItemTypeAdapter(gson)
        }
        if (PreJsonItem::class.java.isAssignableFrom(raw)){
            return PreJsonItemTypeAdapter(gson)
        }
        if (Collection4JsonItem::class.java.isAssignableFrom(raw)){
            return Collection4JsonItemTypeAdapter(gson)
        }
        return null
    }
}