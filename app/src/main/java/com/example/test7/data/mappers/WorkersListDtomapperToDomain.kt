package com.example.test7.data.mappers

import com.example.test7.data.model.WorkersListPageDto
import com.example.test7.domain.model.WorkersAndProfessionsEntity

fun WorkersListPageDto.toDomain():WorkersAndProfessionsEntity{
    return WorkersAndProfessionsEntity(
        professionsList = professionsList,
        workersList = workersList.map {
            it.toDomain()
        }
    )
}