package com.example.hackathon.utility

import com.google.gson.Gson
import com.google.gson.JsonParseException
import java.lang.reflect.Type

fun <T> String.toGson(type: Type): T? {
    return try {
        Gson().fromJson<T>(this, type)
    } catch (e: JsonParseException) {
        e.printStackTrace()
        null
    } catch (e: NullPointerException) {
        null
    } catch (e: Exception) {
        null
    }
}