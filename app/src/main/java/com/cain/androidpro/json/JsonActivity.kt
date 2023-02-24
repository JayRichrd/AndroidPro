package com.cain.androidpro.json

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cain.androidpro.R
import com.google.gson.GsonBuilder

class JsonActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val TAG = "JsonActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)
        findViewById<Button>(R.id.btn_parse).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_parse -> {
                parseTest()
            }
        }
    }

    private fun parseTest() {
        val gson = GsonBuilder().registerTypeAdapterFactory(CustomTypeAdapterFactory()).create()
        val jsonStr =
            "{\"block\":[{\"id\":\"1\",\"name\":\"菜鸟教程\",\"url\":\"www.runoob.com\"},{\"id\":\"2\",\"name\":\"菜鸟工具\",\"url\":\"c.runoob.com\"},{\"id\":\"3\",\"name\":\"Google\",\"url\":\"www.google.com\"}],\"obj\":{\"id\":\"1\",\"name\":\"高手直通\",\"url\":\"www.baidu.com\"}}"
        val preJsonItem = gson.fromJson(jsonStr, PreJsonItem::class.java)
        Log.i(TAG, "parseTest: preJsonItem = $preJsonItem")
    }
}