package com.example.test7.presenter.state.register

import com.example.test7.presenter.model.UserAuthenticator

data class RegisterState(
    val isLoading:Boolean = false,
    val userAuthenticator:UserAuthenticator? = null,
    val errorMessage:String? = null
)