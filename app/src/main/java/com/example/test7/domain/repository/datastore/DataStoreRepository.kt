package com.example.test7.domain.repository.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveToken(key: Preferences.Key<String>,token:String)

    fun readToken(key: Preferences.Key<String>) : Flow<String>

    suspend fun clearToken()
}