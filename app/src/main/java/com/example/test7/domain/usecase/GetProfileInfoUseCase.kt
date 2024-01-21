package com.example.test7.domain.usecase

import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.model.WorkerEntity
import com.example.test7.domain.repository.home.WorkersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileInfoUseCase @Inject constructor(
    private val workersListRepository: WorkersRepository
) {
    suspend operator fun invoke(): Flow<ResultWrapper<WorkerEntity>> {
        return workersListRepository.getProfileInfo()
    }
}