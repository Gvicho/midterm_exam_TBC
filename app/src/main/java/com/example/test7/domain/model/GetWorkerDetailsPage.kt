package com.example.test7.domain.model

class GetWorkerDetailsPage(
    val workersInfo: WorkerEntity,
    val monthRateData:List<Float>?,
    val lastWorkMonthData: GetLastWorkDate
)