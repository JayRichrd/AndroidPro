package com.cain.androidpro.json

import com.google.gson.annotations.SerializedName

data class JsonItem(
    @SerializedName("id") var id: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("url") var url: String = "",
)
