package com.example.test7.presenter.screen.login

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.usecase.LoginUserUseCase
import com.example.test7.domain.usecase.SaveTokenUseCase
import com.example.test7.presenter.event.login.LoginEvent
import com.example.test7.presenter.model.UserAuthenticator
import com.example.test7.presenter.state.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
):ViewModel() {

    private val _loginUIState =  MutableStateFlow(LoginState())
    val loginUIState :StateFlow<LoginState> = _loginUIState

    private val _loginPageNavigationEvent = MutableSharedFlow<LoginNavigationEvent>()
    val loginPageNavigationEvent get() = _loginPageNavigationEvent

    fun onEvent(event:LoginEvent){
        when(event){
            is LoginEvent.LoginUserEvent -> {
                loginUser(event.email,event.password,event.rememberUserChecked)
            }
            LoginEvent.MoveUserToRegistrationEvent -> {
                setNavigationEventFlowToRegister()
            }
            LoginEvent.ResetErrorStatus -> {
                updateErrorMessage(null)
            }
        }
    }

    private fun loginUser(email:String,password:String,rememberUserChecked:Boolean){
        viewModelScope.launch{
            loginUserUseCase(email,password).collect{ result ->
                when(result){
                    is ResultWrapper.Error -> {
                        _loginUIState.update {
                            it.copy(errorMessage = result.errorMessage)
                        }
                    }
                    is ResultWrapper.Loading -> {
                        _loginUIState.update {
                            it.copy(isLoading = result.loading)
                        }
                    }
                    is ResultWrapper.Success -> {
                        val token = result.data?.user?.uid.toString()  // we gonna use user id as token for now
                        _loginUIState.update {
                            d("tag123",token?:"EmptyToken")
                            it.copy(accessToken = token ?: "")
                        }
                        if(rememberUserChecked)saveTokenInDataStoreIfSelectedSettings(token)
                        setNavigationEventFlowToHome()
                    }
                }
            }
        }
    }

    private fun saveTokenInDataStoreIfSelectedSettings(token:String){
        viewModelScope.launch {
            saveTokenUseCase(token)
        }
    }

    private fun setNavigationEventFlowToHome(){
        viewModelScope.launch {
            _loginPageNavigationEvent.emit(LoginNavigationEvent.NavigateToHomePageEvent)
        }
    }

    private fun setNavigationEventFlowToRegister(){
        viewModelScope.launch {
            _loginPageNavigationEvent.emit(LoginNavigationEvent.NavigateToRegistrationEvent)
        }
    }

    private fun updateErrorMessage(message:String?){
        _loginUIState.update {
            it.copy(errorMessage = message)
        }
    }

    sealed interface LoginNavigationEvent{
        data object NavigateToHomePageEvent:LoginNavigationEvent
        data object NavigateToRegistrationEvent:LoginNavigationEvent
    }
}