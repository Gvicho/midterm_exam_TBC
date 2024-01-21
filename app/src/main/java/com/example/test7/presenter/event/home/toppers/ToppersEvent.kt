package com.example.test7.presenter.event.home.toppers

sealed class ToppersEvent {
    data class OpenWorkersDetails(val workerId:Int):ToppersEvent()
    data object ResetErrorMessageToNull: ToppersEvent()
}