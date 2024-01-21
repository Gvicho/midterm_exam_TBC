package com.example.test7.domain.usecase

import com.example.test7.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class ClearTokenUseCase@Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend operator fun invoke() =dataStoreRepository.clearToken()

}