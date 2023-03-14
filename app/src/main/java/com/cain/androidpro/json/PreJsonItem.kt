package com.cain.androidpro.json

import com.google.gson.JsonObject

data class PreJsonItem(var list: MutableList<JsonItem> = mutableListOf(), var jsonObj: JsonObject? = null)