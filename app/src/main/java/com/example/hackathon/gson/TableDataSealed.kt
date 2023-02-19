package com.example.hackathon.gson

data class TableDataSealed(val array: ArrayList<TableObject>)
data class TableObject(
    val conversion_rate_per_user: Double,
    val conversion_rate_total: Double,
    val page: String,
    val ptype: String,
    val views_per_user: Double
)