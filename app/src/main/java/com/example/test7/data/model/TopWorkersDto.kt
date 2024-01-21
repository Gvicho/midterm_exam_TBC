package com.example.test7.data.model

import com.squareup.moshi.Json

data class TopWorkersDto(
    @Json(name = "top_workers_in_professions_list")
    val topWorkersList:List<WorkerDto>
)