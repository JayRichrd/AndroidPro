package com.cain.androidpro.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JetpackViewModel : ViewModel() {
    val data: MutableLiveData<String> = MutableLiveData()
    fun loadData() {
        data.postValue("test data from repository")
    }
}