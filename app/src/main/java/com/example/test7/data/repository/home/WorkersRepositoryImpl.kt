package com.example.test7.data.repository.home

import com.example.test6.data.common.ResultWrapper
import com.example.test7.data.common.HandleResponse
import com.example.test7.data.common.mapResultWrapper
import com.example.test7.data.mappers.toDomain
import com.example.test7.data.service.WorkersListService
import com.example.test7.domain.model.GetWorkerDetailsPage
import com.example.test7.domain.model.TopWorkers
import com.example.test7.domain.model.WorkerEntity
import com.example.test7.domain.model.WorkersAndProfessionsEntity
import com.example.test7.domain.repository.home.WorkersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkersRepositoryImpl @Inject constructor(
    private val workersListService: WorkersListService,
    private val handleResponse: HandleResponse,
) :WorkersRepository {

    override suspend fun getWorkersList(): Flow<ResultWrapper<WorkersAndProfessionsEntity>> {


        return handleResponse.safeApiCall {
            workersListService.getWorkersList()
        }.mapResultWrapper {
            it.toDomain()
        }
    }

    override suspend fun getTopperWorkers(): Flow<ResultWrapper<TopWorkers>> {
        return handleResponse.safeApiCall {
            workersListService.getTopWorkersList()
        }.mapResultWrapper {
            it.toDomain()
        }
    }

    override suspend fun getProfileInfo(): Flow<ResultWrapper<WorkerEntity>> {
        return handleResponse.safeApiCall {
            workersListService.getProfileInfo()
        }.mapResultWrapper {
            it.toDomain()
        }
    }

    override suspend fun getWorkersDetails(id:Int): Flow<ResultWrapper<GetWorkerDetailsPage>> {
        return handleResponse.safeApiCall {
            workersListService.getWorkerDetails(id)
        }.mapResultWrapper {
            it.toDomain()
        }
    }


}