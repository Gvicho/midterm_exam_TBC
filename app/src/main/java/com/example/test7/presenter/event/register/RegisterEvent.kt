package com.example.test7.presenter.event.register

import android.provider.ContactsContract.CommonDataKinds.Email

sealed class RegisterEvent {
    data class RegisterUser(val email: String,val password:String):RegisterEvent()
    data class MoveBackToLogin(val email: String,val password:String):RegisterEvent()
    data object ResetErrorMessage:RegisterEvent()
}