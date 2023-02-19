package com.example.hackathon.gson

data class TouchSealed(val array: ArrayList<TouchObject>)
data class TouchObject(
    val num_conversions: Double,
    val num_touchpoints: Int
)