package com.example.test7.presenter.event.home.profile

sealed class ProfileEvent {
    data object ResetErrorMessageToNull: ProfileEvent()
}