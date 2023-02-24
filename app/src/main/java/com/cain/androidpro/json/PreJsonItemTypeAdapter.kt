package com.cain.androidpro.json

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class PreJsonItemTypeAdapter<T>(private val gson: Gson) : TypeAdapter<T>() {
    companion object {
        private const val TAG = "PreJsonItemTypeAdapter"
    }

    override fun write(out: JsonWriter, value: T) {
        TODO("Not yet implemented")
    }

    override fun read(jsonReader: JsonReader): T? {
        var fielName = ""
        val preJsonItem = PreJsonItem()
        try {
            jsonReader.beginObject()
            val fieldList = mutableListOf<String>()
            while (jsonReader.hasNext()) {
                fielName = jsonReader.nextName()
                fieldList.add(fielName)
                val token = jsonReader.peek()
                Log.i(TAG, "read: token = $token, fieldName = $fielName")
                when (fielName) {
                    "block" -> {
                        if (token == JsonToken.BEGIN_ARRAY) {
                            val typeAdapter = gson.getAdapter(Collection4JsonItem::class.java)
                            val collection4JsonItem = typeAdapter.read(jsonReader)
                            Log.i(TAG, "read: list = ${collection4JsonItem.list}")
                            preJsonItem.list = collection4JsonItem.list
                        }
                    }
                    "obj" -> {
                        if (token == JsonToken.BEGIN_OBJECT) {
                            val typeAdapter = gson.getAdapter(JsonObject::class.java)
                            val jsonObj = typeAdapter.read(jsonReader)
                            Log.i(TAG, "read: jsonObj = $jsonObj")
                        }
                    }
                    else -> {
                        jsonReader.skipValue()
                    }
                }
            }
            Log.i(TAG, "read: field list = $fieldList")
            jsonReader.endObject()
        } catch (e: Throwable) {
            Log.e(TAG, "read: fieldName = $fielName, errorMsg = ${e.localizedMessage}")
        }
        return preJsonItem as? T
    }
}