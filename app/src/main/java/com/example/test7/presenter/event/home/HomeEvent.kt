package com.example.test7.presenter.event.home

sealed class HomeEvent {
    data object LogOutAndClearToken:HomeEvent()
}