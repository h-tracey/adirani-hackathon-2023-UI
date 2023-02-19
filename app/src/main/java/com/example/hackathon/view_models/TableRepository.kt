package com.example.hackathon.view_models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.hackathon.models.DataClassModels
import com.example.hackathon.utility.Event
import com.example.hackathon.core.MyApplication
import com.example.hackathon.gson.RankingListModelSealed
import com.example.hackathon.utility.Constant
import com.google.gson.Gson
import com.example.hackathon.api.RetrofitConnectionHelper
import com.example.hackathon.utility.ViewUtility
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.collections.ArrayList
import kotlin.collections.arrayListOf


class TableRepository : KoinComponent {
    private lateinit var mEvents: MutableLiveData<Event>
    private lateinit var mContext: Context
    private val retrofitRequestsPile = arrayListOf<RetrofitConnectionHelper>()
    private val gson by lazy { Gson() }
    private var dataList: ArrayList<RankingListModelSealed.RankingBean> = arrayListOf()


    fun setData(context: MyApplication, events: MutableLiveData<Event>) {
        mContext = context
        mEvents = events
    }


    private fun sendEvent(type: Int, input: Any) {
        sendEvent(type, input, null)
    }

    private fun sendEvent(type: Int, input: Any, input2: Any?) {
        mEvents.value = Event(DataClassModels.EventModel(type, input, input2))
    }


    fun loadScatterRetrofit() {
        val mRetrofitHelper = get<RetrofitConnectionHelper>()
        retrofitRequestsPile.add(mRetrofitHelper)
        mRetrofitHelper.setErrorCallback { code -> handleNetworkError(code) }
        mRetrofitHelper.setConnectTypes(Constant.RETROFIT_GET_TYPECODE)
        mRetrofitHelper.setGsonCallback { response ->
            sendEvent(TableViewModel.SCATTER_DATA,response)
        }
        mRetrofitHelper.setUrl(
            "https://9da8-5-61-126-202.eu.ngrok.io/scatter-views?data=true", null, null
        )
        mRetrofitHelper.request()
    }

    fun loadConversionRetrofit() {
        val mRetrofitHelper = get<RetrofitConnectionHelper>()
        retrofitRequestsPile.add(mRetrofitHelper)
        mRetrofitHelper.setErrorCallback { code -> handleNetworkError(code) }
        mRetrofitHelper.setConnectTypes(Constant.RETROFIT_GET_TYPECODE)
        mRetrofitHelper.setGsonCallback { response ->
            sendEvent(TableViewModel.CONVERSION_DATA,response)
        }
        mRetrofitHelper.setUrl(
            "https://9da8-5-61-126-202.eu.ngrok.io/conversion/count", null, null
        )
        mRetrofitHelper.request()

    }

    fun requestTouchDataRetrofit() {
        val mRetrofitHelper = get<RetrofitConnectionHelper>()
        retrofitRequestsPile.add(mRetrofitHelper)
        mRetrofitHelper.setErrorCallback { code -> handleNetworkError(code) }
        mRetrofitHelper.setConnectTypes(Constant.RETROFIT_GET_TYPECODE)
        mRetrofitHelper.setGsonCallback { response ->
            sendEvent(TableViewModel.TOUCH_DATA,response)
        }
        mRetrofitHelper.setUrl(
            "https://9da8-5-61-126-202.eu.ngrok.io/touchpoints", null, null
        )
        mRetrofitHelper.request()
    }

    private fun handleNetworkError(code: Int) {
        ViewUtility.showToast("network errors occurred error code:$code", mContext)
    }

    fun finish() {
        repeat(retrofitRequestsPile.size) {
            retrofitRequestsPile[it].unsubscribeObserver()
        }
    }

    private fun clearAllData() {
        dataList.clear()
    }




}
