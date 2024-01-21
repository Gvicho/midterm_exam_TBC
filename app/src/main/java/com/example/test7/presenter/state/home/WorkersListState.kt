package com.example.test7.presenter.state.home

import com.example.test7.presenter.model.workers_list.WorkersListObjectPresenter

data class WorkersListState(
    val isLoading:Boolean = false,
    val isSuccess:List<WorkersListObjectPresenter>? = null,
    val errorMessage:String? = null
)