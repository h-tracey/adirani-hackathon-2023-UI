package com.example.hackathon.gson


sealed class BaseModel {
    data class StatusBean(val code: Int = 0, val message: String = "")
}