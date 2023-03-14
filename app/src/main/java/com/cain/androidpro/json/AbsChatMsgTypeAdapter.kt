package com.cain.androidpro.json

import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

abstract class AbsChatMsgTypeAdapter<T>(var gson: Gson, var delegate: TypeAdapter<T>?) : TypeAdapter<T>() {
    companion object {
        const val TAG = "ChatMsgTypeAdapter"
    }

    private val numberList = mutableListOf(ChatMsgFieldType.INT, ChatMsgFieldType.LONG, ChatMsgFieldType.FLOAT, ChatMsgFieldType.DOUBLE)

    override fun write(jsonWriter: JsonWriter, value: T) {
        delegate?.write(jsonWriter, value)
    }

    override fun read(jsonReader: JsonReader): T? {
        return delegate?.read(jsonReader)
    }

    protected fun onCheckTypeFail(field: String, jsonReader: JsonReader) {
        Log.e(TAG, "${this::class.java.simpleName}#onCheckTypeFail: key[$field] type check fail.")
        jsonReader.skipValue()
        throw Throwable("onCheckTypeFail: key[$field] type check fail.")
    }

    protected fun checkType(token: JsonToken, jsonReader: JsonReader) = jsonReader.peek() == token

    @Suppress("UNCHECKED_CAST")
    protected fun <R> safeParseNumOrString(gson: Gson, fieldName: String, jsonReader: JsonReader, chatMsgFielType: ChatMsgFieldType): R? {
        if (chatMsgFielType != ChatMsgFieldType.STRING
            && chatMsgFielType != ChatMsgFieldType.LONG
            && chatMsgFielType != ChatMsgFieldType.FLOAT
            && chatMsgFielType != ChatMsgFieldType.DOUBLE
            && chatMsgFielType != ChatMsgFieldType.INT) {
            Log.e(TAG, "${this::class.java.simpleName}#safeParseNumOrString: fieldName[$fieldName], type of field to be parsed is not supported.")
            return null
        }

        val actualFieldTypeToken = jsonReader.peek()
        var actualFieldType = chatMsgFielType
        var value: Any? = null
        try {
            if (actualFieldTypeToken == JsonToken.STRING) {
                actualFieldType = ChatMsgFieldType.STRING
                value = gson.getAdapter(String::class.java).read(jsonReader)
            } else if (actualFieldTypeToken == JsonToken.NUMBER) {
                actualFieldType = chatMsgFielType
                value = try2ParseNumber(gson, jsonReader, chatMsgFielType)
                if (value == null) {
                    val remainNumberList = numberList - chatMsgFielType
                    remainNumberList.forEach breakLabel@{ fieldType ->
                        actualFieldType = fieldType
                        value = try2ParseNumber(gson, jsonReader, fieldType)
                        if (value != null) {
                            return@breakLabel
                        }
                    }
                }
            }
        } catch (throwable: Throwable) {
            Log.e(TAG, "${this::class.java.simpleName}#safeParseNumOrString: fiele[$fieldName], errorMsg = ${throwable.localizedMessage}")
        }

        value?.let {
            if (actualFieldType == chatMsgFielType) {
                when (chatMsgFielType) {
                    ChatMsgFieldType.STRING -> {
                        return it as? R
                    }
                    ChatMsgFieldType.INT -> {
                        return (it as? Int) as? R
                    }
                    ChatMsgFieldType.LONG -> {
                        return (it as? Long) as? R
                    }
                    ChatMsgFieldType.FLOAT -> {
                        return (it as? Float) as? R
                    }
                    ChatMsgFieldType.DOUBLE -> {
                        return (it as? Double) as? R
                    }
                    else -> {
                        Log.e(TAG, "${this::class.java.simpleName}#safeParseNumOrString: fieldName = $fieldName, only pare string, int, long.")
                        onCheckTypeFail(fieldName, jsonReader)
                        return null
                    }
                }
            }

            when (actualFieldType) {
                ChatMsgFieldType.STRING -> {
                    when (chatMsgFielType) {
                        ChatMsgFieldType.INT -> {
                            return (it as? String)?.toInt() as? R
                        }
                        ChatMsgFieldType.LONG -> {
                            return (it as? String)?.toLong() as? R
                        }
                        ChatMsgFieldType.FLOAT -> {
                            return (it as? String)?.toFloat() as? R
                        }
                        ChatMsgFieldType.DOUBLE -> {
                            return (it as? String)?.toDouble() as? R
                        }
                        else -> {
                            onCheckTypeFail(fieldName, jsonReader)
                            return null
                        }
                    }
                }
                ChatMsgFieldType.INT -> {
                    when (chatMsgFielType) {
                        ChatMsgFieldType.STRING -> {
                            return (it as? Int)?.toString() as? R
                        }
                        ChatMsgFieldType.LONG -> {
                            return (it as? Int)?.toLong() as? R
                        }
                        ChatMsgFieldType.FLOAT -> {
                            return (it as? Int)?.toFloat() as? R
                        }
                        ChatMsgFieldType.DOUBLE -> {
                            return (it as? Int)?.toDouble() as? R
                        }
                        else -> {
                            onCheckTypeFail(fieldName, jsonReader)
                            return null
                        }
                    }
                }
                ChatMsgFieldType.LONG -> {
                    when (chatMsgFielType) {
                        ChatMsgFieldType.STRING -> {
                            return (it as? Long)?.toString() as? R
                        }
                        ChatMsgFieldType.INT -> {
                            return (it as? Long)?.toInt() as? R
                        }
                        ChatMsgFieldType.FLOAT -> {
                            return (it as? Long)?.toFloat() as? R
                        }
                        ChatMsgFieldType.DOUBLE -> {
                            return (it as? Long)?.toDouble() as? R
                        }
                        else -> {
                            onCheckTypeFail(fieldName, jsonReader)
                            return null
                        }
                    }
                }
                ChatMsgFieldType.FLOAT -> {
                    when (chatMsgFielType) {
                        ChatMsgFieldType.STRING -> {
                            return (it as? Float)?.toString() as? R
                        }
                        ChatMsgFieldType.INT -> {
                            return (it as? Float)?.toInt() as? R
                        }
                        ChatMsgFieldType.LONG -> {
                            return (it as? Float)?.toLong() as? R
                        }
                        ChatMsgFieldType.DOUBLE -> {
                            return (it as? Float)?.toDouble() as? R
                        }
                        else -> {
                            onCheckTypeFail(fieldName, jsonReader)
                            return null
                        }
                    }
                }
                ChatMsgFieldType.DOUBLE -> {
                    when (chatMsgFielType) {
                        ChatMsgFieldType.STRING -> {
                            return (it as? Double)?.toString() as? R
                        }
                        ChatMsgFieldType.INT -> {
                            return (it as? Double)?.toInt() as? R
                        }
                        ChatMsgFieldType.LONG -> {
                            return (it as? Double)?.toLong() as? R
                        }
                        ChatMsgFieldType.FLOAT -> {
                            return (it as? Double)?.toFloat() as? R
                        }
                        else -> {
                            onCheckTypeFail(fieldName, jsonReader)
                            return null
                        }
                    }
                }
                else -> {
                    onCheckTypeFail(fieldName, jsonReader)
                    return null
                }
            }
        }

        onCheckTypeFail(fieldName, jsonReader)
        return null
    }

    private fun try2ParseNumber(gson: Gson, jsonReader: JsonReader, chatMsgFielType: ChatMsgFieldType): Any? {
        var value: Any? = null
        try {
            val adapter = gson.getAdapter(chatMsgFielType.obj::class.java)
            value = adapter.read(jsonReader)
        } catch (throwable: Throwable) {
            Log.e(TAG, "${this::class.java.simpleName}#try2ParseFloat: errorMsg = ${throwable.localizedMessage}")
        }
        return value
    }

    @Suppress("UNCHECKED_CAST")
    protected fun safeParseBoolean(gson: Gson, fieldName: String, jsonReader: JsonReader, chatMsgFielType: ChatMsgFieldType): Boolean {
        if (chatMsgFielType != ChatMsgFieldType.BOOLEAN) {
            Log.e(TAG, "${this::class.java.simpleName}#safeParseBoolean: fieldName[$fieldName], type of field to be parsed is not supported.")
            return false
        }
        val actualFieldTypeToken = jsonReader.peek()
        var actualFieldType = chatMsgFielType
        var value: Any? = null
        try {
            if (actualFieldTypeToken == JsonToken.BOOLEAN) {
                actualFieldType = ChatMsgFieldType.BOOLEAN
                value = gson.getAdapter(Boolean::class.java).read(jsonReader)
            } else if (actualFieldTypeToken == JsonToken.STRING) {
                actualFieldType = ChatMsgFieldType.STRING
                value = gson.getAdapter(String::class.java).read(jsonReader)
            }
        } catch (throwable: Throwable) {
            Log.e(TAG, "${this::class.java.simpleName}#safeParseBoolean: errorMsg = ${throwable.localizedMessage}")
        }

        value?.let { vl ->
            if (actualFieldType == ChatMsgFieldType.BOOLEAN) {
                return vl as? Boolean ?: false
            }
            (vl as? String)?.let { vlStr ->
                return "true" === vlStr
            }
            return false
        }
        return false
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> try2WritePrimitiveField(gson: Gson, jsonWriter: JsonWriter, key: String, field: T?, chatMsgFieldType: ChatMsgFieldType) {
        field?.let {
            jsonWriter.name(key)
            val adapter = gson.getAdapter(chatMsgFieldType.obj::class.java) as? TypeAdapter<T>
            adapter?.write(jsonWriter, it)
        }
    }
}