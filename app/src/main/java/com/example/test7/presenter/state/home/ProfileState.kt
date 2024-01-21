package com.example.test7.presenter.state.home

import com.example.test7.presenter.model.WorkerPresenter

data class ProfileState(
    val isLoading:Boolean = false,
    val isSuccess:WorkerPresenter? = null,
    val errorMessage:String? = null
) {
}