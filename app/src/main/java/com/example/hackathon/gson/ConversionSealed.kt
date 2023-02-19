package com.example.hackathon.gson

data class ConversionSealed(val array: ArrayList<ConversionObject>)
data class ConversionObject(
    val conversions: Double,
    val default_channel_group: String
)