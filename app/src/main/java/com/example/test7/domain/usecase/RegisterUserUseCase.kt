package com.example.test7.domain.usecase

import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.repository.firebase_authentication.FireBaseAuthenticationRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RegisterUserUseCase @Inject constructor(private val authRepository: FireBaseAuthenticationRepository) {

    operator fun invoke(email:String,password:String) : Flow<ResultWrapper<AuthResult>> {
            return authRepository.registerUser(email, password)
    }

}