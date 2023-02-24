package com.cain.androidpro.json

import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class JsonItemTypeAdapter<T>(private val gson: Gson) : TypeAdapter<T>() {
    companion object {
        private const val TAG = "JsonItemTypeAdapter"
    }

    override fun write(out: JsonWriter, value: T) {
        TODO("Not yet implemented")
    }

    override fun read(jsonReader: JsonReader): T? {
        var fielName = ""
        val jsonItem = JsonItem()
        try {
            jsonReader.beginObject()
            val fieldList = mutableListOf<String>()
            while (jsonReader.hasNext()) {
                fielName = jsonReader.nextName()
                fieldList.add(fielName)
                when (fielName) {
                    "name" -> {
                        jsonItem.name = gson.getAdapter(String::class.java).read(jsonReader)
                    }
                    "id" -> {
                        jsonItem.id = gson.getAdapter(String::class.java).read(jsonReader)
                    }
                    "url" -> {
                        jsonItem.url = gson.getAdapter(String::class.java).read(jsonReader)
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
        return jsonItem as? T
    }
}