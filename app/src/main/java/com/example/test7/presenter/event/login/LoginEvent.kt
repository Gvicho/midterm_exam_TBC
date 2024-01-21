package com.example.test7.presenter.event.login

sealed class LoginEvent {
    data class LoginUserEvent(val email:String,val password:String,val rememberUserChecked:Boolean) :LoginEvent()
    data object MoveUserToRegistrationEvent:LoginEvent()
    data object ResetErrorStatus:LoginEvent()
}