package com.example.test7.presenter.event.details

sealed class WorkerDetailsEvent {
    data class LoadUserDetails(val id:Int):WorkerDetailsEvent()
    data object ResetErrorMessageToNull:WorkerDetailsEvent()
}