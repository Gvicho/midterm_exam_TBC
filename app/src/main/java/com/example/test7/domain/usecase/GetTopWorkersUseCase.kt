package com.example.test7.domain.usecase

import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.model.TopWorkers
import com.example.test7.domain.repository.home.WorkersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopWorkersUseCase @Inject constructor(
    private val workersListRepository: WorkersRepository
) {

    suspend operator fun invoke(): Flow<ResultWrapper<TopWorkers>> {
        return workersListRepository.getTopperWorkers()
    }

}