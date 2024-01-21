package com.example.test7.data.mappers

import com.example.test7.data.model.details.WorkerDetailsPageDto
import com.example.test7.domain.model.GetWorkerDetailsPage

fun WorkerDetailsPageDto.toDomain(): GetWorkerDetailsPage{
    return GetWorkerDetailsPage(
        workersInfo = workersInfo.toDomain(),
        monthRateData = monthRateData,
        lastWorkMonthData = lastWorkMonthData.toDomain()
    )
}