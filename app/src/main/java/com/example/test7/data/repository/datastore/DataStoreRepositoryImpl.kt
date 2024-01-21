package com.example.test7.data.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.test7.domain.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val datastore:DataStore<Preferences>) :DataStoreRepository{

    override suspend fun saveToken(key: Preferences.Key<String>, token: String) {
        datastore.edit { settings->
            settings[key] = token
        }
    }

    override fun readToken(key: Preferences.Key<String>): Flow<String> = datastore.data
        .map {
        it[key] ?: ""
    }

    override suspend fun clearToken() {
        datastore.edit {settings->
            settings.clear()
        }
    }
}