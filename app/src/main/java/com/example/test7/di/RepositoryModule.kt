package com.example.test7.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.test7.data.common.HandleResponse
import com.example.test7.data.repository.datastore.DataStoreRepositoryImpl
import com.example.test7.data.repository.firebase.FireBaseAuthenticationRepositoryImpl
import com.example.test7.data.repository.home.WorkersRepositoryImpl
import com.example.test7.data.service.WorkersListService
import com.example.test7.domain.repository.datastore.DataStoreRepository
import com.example.test7.domain.repository.firebase_authentication.FireBaseAuthenticationRepository
import com.example.test7.domain.repository.home.WorkersRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>):DataStoreRepository {
        return DataStoreRepositoryImpl(
            datastore = dataStore
        )
    }

    @Provides
    @Singleton
    fun provideFireBaseRepository(firebaseAuth: FirebaseAuth):FireBaseAuthenticationRepository {
        return FireBaseAuthenticationRepositoryImpl(
            firebaseAuth = firebaseAuth
        )
    }

    @Singleton
    @Provides
    fun provideWorkersRepository(
        workersListService: WorkersListService,
        handleResponse: HandleResponse,
    ): WorkersRepository {
        return WorkersRepositoryImpl(
            workersListService = workersListService,
            handleResponse = handleResponse
        )
    }

}