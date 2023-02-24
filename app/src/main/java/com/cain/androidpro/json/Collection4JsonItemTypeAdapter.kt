package com.cain.androidpro.json

import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class Collection4JsonItemTypeAdapter<T>(private val gson: Gson) : TypeAdapter<T>() {
    companion object {
        private const val TAG = "Collection4JsonItemTypeAdapter"
    }

    override fun write(out: JsonWriter, value: T) {
        TODO("Not yet implemented")
    }

    override fun read(jsonReader: JsonReader): T? {
        val collection4JsonItem = Collection4JsonItem()
        collection4JsonItem.list.clear()
        try {
            jsonReader.beginArray()
            val fieldList = mutableListOf<String>()
            while (jsonReader.hasNext()) {
                val jsonItem = gson.getAdapter(JsonItem::class.java).read(jsonReader)
                Log.i(TAG, "read: jsonItem = $jsonItem")
                collection4JsonItem.list.add(jsonItem)
            }
            Log.i(TAG, "read: field list = $fieldList")
            jsonReader.endArray()
        } catch (e: Throwable) {
            Log.e(TAG, "read: errorMsg = ${e.localizedMessage}")
        }
        return collection4JsonItem as? T
    }
}