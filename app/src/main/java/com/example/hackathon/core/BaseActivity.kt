package com.example.hackathon.core

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hackathon.api.RetrofitConnectionHelper
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {
    @JvmField
    protected var mCompositeDisposable = CompositeDisposable()
    protected val retrofitRequestsPile = arrayListOf<RetrofitConnectionHelper?>()

    private var mStateSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStateSaved = false
    }

    override fun onStart() {
        super.onStart()
        mStateSaved = false
    }

    override fun onResume() {
        super.onResume()
        mStateSaved = false
    }

    override fun onStop() {
        super.onStop()
        mStateSaved = true
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mStateSaved = true
    }


    protected open fun isFragmentStateSaved(): Boolean {
        return mStateSaved
    }

    open fun gotoNextPage(className: Class<*>, extrasKey: String?, title: String?) {
        val bundle = Bundle()
        bundle.putString(extrasKey, title)
        gotoNextPage(className, bundle)
    }

    open fun gotoNextPage(className: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(this, className)
        if (bundle != null) intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribeRetrofitObserver()
        mCompositeDisposable.clear()
        mCompositeDisposable.dispose()
    }

    private fun unsubscribeRetrofitObserver() {
        repeat(retrofitRequestsPile.size) { position ->
            retrofitRequestsPile[position].let { it?.unsubscribeObserver() }
        }
    }
}