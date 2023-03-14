package com.cain.androidpro.json

import com.google.gson.stream.JsonToken

enum class ChatMsgFieldType(val token: JsonToken, val obj: Any) {
    INT(JsonToken.NUMBER, 1),
    LONG(JsonToken.NUMBER, 1L),
    FLOAT(JsonToken.NUMBER, 1F),
    DOUBLE(JsonToken.NUMBER, 0.0),
    STRING(JsonToken.STRING, "String"),
    BOOLEAN(JsonToken.BOOLEAN, true)
}