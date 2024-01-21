package com.example.test7.domain.repository.firebase_authentication

import com.example.test6.data.common.ResultWrapper
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface FireBaseAuthenticationRepository {
    fun registerUser(email:String, password:String): Flow<ResultWrapper<AuthResult>>
    fun loginUser(email:String, password:String): Flow<ResultWrapper<AuthResult>>
}