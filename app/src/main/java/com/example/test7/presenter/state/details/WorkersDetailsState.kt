package com.example.test7.presenter.state.details

import com.example.test7.presenter.model.worker_details.WorkerDetailsListObject

data class WorkersDetailsState(
    val isLoading:Boolean = false,
    val isSuccess: List<WorkerDetailsListObject>? = null,
    val errorMessage:String? = null
) {
}