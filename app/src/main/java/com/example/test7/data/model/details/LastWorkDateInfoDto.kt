package com.example.test7.data.model.details

import com.squareup.moshi.Json

data class LastWorkDateInfoDto(
    @Json(name = "year")
    val lastYear:Int ,
    @Json(name = "month")
    val lastMonth:Int,
    @Json(name = "status")
    val status:String
) {
}