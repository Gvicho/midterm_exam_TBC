package com.example.test7.data.mappers

import com.example.test7.data.model.WorkerDto
import com.example.test7.domain.model.WorkerEntity

fun WorkerDto.toDomain():WorkerEntity{
    return WorkerEntity(
        id = id,
        avatar = avatar,
        name = name,
        profession = profession,
        rate = feedBackRate,
        averageHoursWorked = averageHoursWorkedInADay
    )
}