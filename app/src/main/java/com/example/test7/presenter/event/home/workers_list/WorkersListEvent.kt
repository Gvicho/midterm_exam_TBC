package com.example.test7.presenter.event.home.workers_list

sealed class WorkersListEvent {
    data class OpenWorkersDetails(val workerId:Int):WorkersListEvent()
    data class FilterWorkersListByProfession(val profession:String):WorkersListEvent()
    data object ResetErrorMessageToNull:WorkersListEvent()
}