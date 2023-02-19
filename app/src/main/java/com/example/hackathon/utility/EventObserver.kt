package com.example.hackathon.utility

import androidx.lifecycle.Observer
import com.example.hackathon.models.DataClassModels

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
class EventObserver(private val onEventUnhandledContent: (DataClassModels.EventModel) -> Unit) : Observer<Event> {
    override fun onChanged(event: Event?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}
