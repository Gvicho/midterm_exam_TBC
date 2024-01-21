package com.example.test7.presenter.state.login

data class LoginState(
    val isLoading :Boolean = false,
    val accessToken :String? = null,
    val errorMessage :String? = null
)