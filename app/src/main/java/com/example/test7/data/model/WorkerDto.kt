package com.example.test7.data.model

import com.squareup.moshi.Json

data class WorkerDto(
    val id:Int,
    val name:String,
    val profession:String,
    @Json(name = "feed_back_rate")
    val feedBackRate:Double? = null,
    @Json(name = "average_hours_in_a_day")
    val averageHoursWorkedInADay:Double,
    @Json(name = "photo")
    val avatar:String? = null
)