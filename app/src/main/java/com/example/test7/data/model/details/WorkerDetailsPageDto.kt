package com.example.test7.data.model.details

import com.example.test7.data.model.WorkerDto
import com.squareup.moshi.Json

class WorkerDetailsPageDto(
    @Json(name = "worker_info")
    val workersInfo:WorkerDto,
    @Json(name = "month_rate_scores")
    val monthRateData:List<Float>?,
    @Json(name = "last_work_month")
    val lastWorkMonthData:LastWorkDateInfoDto
) {
}