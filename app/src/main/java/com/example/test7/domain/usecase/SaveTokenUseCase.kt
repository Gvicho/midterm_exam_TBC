package com.example.test7.domain.usecase

import com.example.test7.domain.datstore_key.PreferencesKey
import com.example.test7.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class SaveTokenUseCase@Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend operator fun invoke(token:String) = dataStoreRepository.saveToken(PreferencesKey.TOKEN,token)

}