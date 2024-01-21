package com.example.test7.presenter.state.home

import com.example.test7.presenter.model.WorkerPresenter
import com.example.test7.presenter.model.workers_list.WorkersListObjectPresenter

data class ToppersState(
    val isLoading:Boolean = false,
    val isSuccess:List<WorkerPresenter>? = null,
    val errorMessage:String? = null
)