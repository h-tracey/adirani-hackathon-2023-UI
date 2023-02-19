package com.example.hackathon.core

import android.app.Application
import android.content.Context
import com.example.hackathon.view_models.apiModules
import com.example.hackathon.view_models.repoModules
import com.example.hackathon.utility.RxBus
import com.example.hackathon.view_models.viewModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApplication : Application() {
    private val bus: RxBus by lazy { RxBus() }
    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = this
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MyApplication)
            val list = listOf(viewModules, repoModules, apiModules)

            modules(list)
        }
    }


    fun bus(): RxBus {
        return bus
    }


    companion object {
        lateinit var instance: MyApplication
            private set

        @JvmStatic
        lateinit var appContext: Context
            private set
    }
}