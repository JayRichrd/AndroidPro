package com.cain.androidpro.widget.rv

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cain.androidpro.R
import com.cain.androidpro.databinding.ActivityRvBinding
import kotlin.random.Random

class RVActivity : AppCompatActivity() {
    private val items by lazy {
        mutableListOf<RvItemEntity>().apply {
            val prefix = this@RVActivity.getString(R.string.rv_item_test_text)
            val textRandom = Random(1000)
            for (i in 0 until 100) {
                add(RvItemEntity("$prefix${textRandom.nextInt()}"))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv)
        initView(ActivityRvBinding.inflate(LayoutInflater.from(this)))
    }

    private fun initView(viewBinding: ActivityRvBinding) {
        val rvAdapter = RVAdapter()
        rvAdapter.items.addAll(items)
        viewBinding.rvList.adapter = rvAdapter
        val lm = LinearLayoutManager(this)
        viewBinding.rvList.layoutManager = lm
    }
}