package com.example.test7.data.repository.firebase

import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.repository.firebase_authentication.FireBaseAuthenticationRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireBaseAuthenticationRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :FireBaseAuthenticationRepository{
    override fun registerUser(email: String, password: String): Flow<ResultWrapper<AuthResult>> = flow {
        try {
            emit(ResultWrapper.Loading(true))
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(ResultWrapper.Success(result))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Unknown error"))
        } finally {
            emit(ResultWrapper.Loading(false))
        }
    }

    override fun loginUser(email: String, password: String): Flow<ResultWrapper<AuthResult>> = flow {
        try {
            emit(ResultWrapper.Loading(true))
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(ResultWrapper.Success(result))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Unknown error"))
        } finally {
            emit(ResultWrapper.Loading(false))
        }
    }
}