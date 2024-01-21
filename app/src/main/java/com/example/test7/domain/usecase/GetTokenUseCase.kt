package com.example.test7.domain.usecase

import com.example.test7.domain.datstore_key.PreferencesKey
import com.example.test7.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor( private val dataStoreRepository: DataStoreRepository) {

    operator fun invoke() =dataStoreRepository.readToken(PreferencesKey.TOKEN)

}