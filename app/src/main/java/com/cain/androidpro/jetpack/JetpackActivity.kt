package com.cain.androidpro.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cain.androidpro.R
import com.orhanobut.logger.Logger

class JetpackActivity : AppCompatActivity() {
    companion object {
        const val TAG = "JetpackActivity"
    }

    private lateinit var vm: JetpackViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                Logger.t(TAG).d("DefaultLifecycleObserver#onCreate#")
            }

            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                Logger.t(TAG).d("DefaultLifecycleObserver#onStart#")
            }

            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                Logger.t(TAG).d("DefaultLifecycleObserver#onResume#")
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                Logger.t(TAG).d("DefaultLifecycleObserver#onPause#")
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                Logger.t(TAG).d("DefaultLifecycleObserver#onStop#")
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                Logger.t(TAG).d("DefaultLifecycleObserver#onDestroy#")
            }
        })
        Logger.t(TAG).d("onCreate#")
        vm = ViewModelProvider(this).get(JetpackViewModel::class.java).apply {
            data.observe(this@JetpackActivity) { data ->
                Logger.t(TAG).d("onCreate#onChanged# data: $data")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Logger.t(TAG).d("onPause#")
    }

    override fun onResume() {
        super.onResume()
        Logger.t(TAG).d("onResume#")
        vm.loadData()
    }
}