package com.example.test7.data.model

import com.squareup.moshi.Json

data class WorkersListPageDto(
    @Json(name = "worker_options")
    val professionsList: List<String>,
    @Json(name = "workers_list")
    val workersList: List<WorkerDto>
) {
}