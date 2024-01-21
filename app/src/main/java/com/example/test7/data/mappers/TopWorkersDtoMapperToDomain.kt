package com.example.test7.data.mappers

import com.example.test7.data.model.TopWorkersDto
import com.example.test7.domain.model.TopWorkers

fun TopWorkersDto.toDomain():TopWorkers{
    return TopWorkers(
        topWorkersList = topWorkersList.map {
            it.toDomain()
        }
    )
}