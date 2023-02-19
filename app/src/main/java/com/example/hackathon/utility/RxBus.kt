package com.example.hackathon.utility

import com.example.hackathon.core.MyApplication.Companion.instance
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus {

    fun sendToPage(pageType: Int) {
        instance.bus().send(pageType)
    }

    private val bus = PublishSubject.create<Any>()
    private fun send(o: Any) {
        bus.onNext(o)
    }
    fun sendEvent(eventType: Int) {
        instance.bus().send(eventType)
    }

    fun toObservable(): Observable<Any> {
        return bus
    }

    companion object {
        const val PAGE_1 = 2001
        const val PAGE_2 = 2002
        const val PAGE_3 = 2003

    }
}