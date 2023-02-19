package com.example.hackathon.view_models

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hackathon.utility.Event
import com.example.hackathon.core.MyApplication

class TableViewModel(private val repository: TableRepository) :
    AndroidViewModel(MyApplication.instance) {
    val mEvents = MutableLiveData<Event>()
    val mContext = getApplication<MyApplication>()

    init {
        repository.setData(mContext, mEvents)
    }


    fun requestScatterDataRetrofit() {
        repository.loadScatterRetrofit()
    }

    fun requestConversionDataRetrofit() {
        repository.loadConversionRetrofit()
    }

    fun finish() {
        repository.finish()
    }

    fun requestTouchDataRetrofit() {
        repository.requestTouchDataRetrofit()
    }

    companion object {
        const val MESSAGE = 201
        const val SCATTER_DATA = 202
        const val CONVERSION_DATA = 203
        const val TOUCH_DATA = 204
    }
}
