package com.example.hackathon.utility

import com.example.hackathon.models.DataClassModels


/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event(private val content: DataClassModels.EventModel) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): DataClassModels.EventModel? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): DataClassModels.EventModel = content
}