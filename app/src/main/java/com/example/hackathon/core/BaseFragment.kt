package com.example.hackathon.core

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.hackathon.api.RetrofitConnectionHelper
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {
    protected var mActivity: Activity? = null
    protected val mCompositeDisposable = CompositeDisposable()
    protected val retrofitRequestsPile = ArrayList<RetrofitConnectionHelper>()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
        mCompositeDisposable.dispose()
        unsubscribeRetrofitObserver()
    }

    private fun unsubscribeRetrofitObserver() {
        for (it in retrofitRequestsPile) {
            it.unsubscribeObserver()
        }
    }

}