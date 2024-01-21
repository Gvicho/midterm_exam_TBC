package com.example.test7.domain.repository.home

import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.model.GetWorkerDetailsPage
import com.example.test7.domain.model.TopWorkers
import com.example.test7.domain.model.WorkerEntity
import com.example.test7.domain.model.WorkersAndProfessionsEntity
import kotlinx.coroutines.flow.Flow

interface WorkersRepository {
    suspend fun getWorkersList(): Flow<ResultWrapper<WorkersAndProfessionsEntity>>
    suspend fun getTopperWorkers():Flow<ResultWrapper<TopWorkers>>
    suspend fun getProfileInfo():Flow<ResultWrapper<WorkerEntity>>
    suspend fun getWorkersDetails(id:Int):Flow<ResultWrapper<GetWorkerDetailsPage>>
}