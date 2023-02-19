package com.example.hackathon.api

import com.example.hackathon.utility.Constant
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitConnectionHelper : KoinComponent {
    private var mUrlFirstSegment: String? = null
    private var mUrlSecondSegment: String? = null
    private var mUrlThirdSegment: String? = null
    private var mObjectCallback: RetrofitCallbacks.ObjectCallback? = null
    private var mParams = hashMapOf<String, String>()
    private var mHttpType = 0
    private var mApiDisposable: Disposable? = null
    private var mErrorCallback: RetrofitCallbacks.RetrofitErrorCallback? = null
    private val retrofit by inject<Retrofit>()
    private var formattedURL = ""
    private var mParamsPlainTxt: String = ""


    private fun onResponseComplete(data: String) {
        try {
            mObjectCallback!!.objectOnResponse(data)
        } catch (e: Exception) {
        }
    }

    fun setConnectTypes(httpType: Int) {
        mHttpType = httpType
    }

    fun setErrorCallback(errorCallback: RetrofitCallbacks.RetrofitErrorCallback?) {
        mErrorCallback = errorCallback
    }

    fun setGsonCallback(ObjectCallback: RetrofitCallbacks.ObjectCallback) {
        mObjectCallback = ObjectCallback
    }

    fun setUrl(urlFirstSegment: String?, urlSecondSegment: String?, urlThirdSegment: String?) {
        mUrlFirstSegment = urlFirstSegment
        mUrlSecondSegment = urlSecondSegment
        mUrlThirdSegment = urlThirdSegment
    }

//    fun setParams(params: Map<String, String>) {
//        mParams = params as HashMap<String, String>
//        mParamsPlainTxt = mParams.toString()
//    }

    fun request() {
        requestAPI()
    }

    private fun requestAPI() {
        when (mHttpType) {
            Constant.RETROFIT_GET_TYPECODE -> sendGetRequest(
                mUrlFirstSegment, mUrlSecondSegment, mUrlThirdSegment, mParams, registerObserver()
            )
            Constant.RETROFIT_POST_TYPECODE -> sendPostRequest(
                mUrlFirstSegment, mUrlSecondSegment, mUrlThirdSegment, mParams, registerObserver()
            )
        }
    }

    private fun sendGetRequest(
        mUrlFirstSegment: String?,
        mUrlSecondSegment: String?,
        mUrlThirdSegment: String?,
        mParams: HashMap<String, String>?,
        responseObserver: Observer<Response<String>>
    ) {
        formattedURL = RetrofitFormatHelper().formatGetUrl(
            mUrlFirstSegment, mUrlSecondSegment, mUrlThirdSegment, mParams
        )!!
        retrofit.create(RetrofitServiceStore.GetRetrofitService::class.java).getCall(formattedURL)
            .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(responseObserver)
    }

    private fun sendPostRequest(
        mUrlFirstSegment: String?,
        mUrlSecondSegment: String?,
        mUrlThirdSegment: String?,
        mParams: java.util.HashMap<String, String>?,
        responseObserver: Observer<Response<String>>
    ) {
        formattedURL = RetrofitFormatHelper().formatPostUrl(
            mUrlFirstSegment, mUrlSecondSegment, mUrlThirdSegment
        )!!
        retrofit.create(RetrofitServiceStore.PostRetrofitService::class.java)
            .postCall(formattedURL, mParams).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(responseObserver)
    }

    private fun registerObserver(): Observer<Response<String>> {
        return object : Observer<Response<String>> {
            override fun onSubscribe(disposable: Disposable) {
                mApiDisposable = disposable
            }

            override fun onNext(stringResponse: Response<String>) {
                unsubscribeObserver()
                try {
                    handleServerStatus(stringResponse)
                } catch (e: Exception) {
                    mErrorCallback?.onNetworkError(404)
                }
            }

            override fun onError(e: Throwable) {
                unsubscribeObserver()
            }

            override fun onComplete() {}
        }
    }

    private fun handleServerStatus(
        stringResponse: Response<String>?
    ) {
        onResponseComplete(stringResponse!!.body()!!)
    }


    fun unsubscribeObserver() {
        if (mApiDisposable != null && !mApiDisposable!!.isDisposed) mApiDisposable!!.dispose()
    }

}