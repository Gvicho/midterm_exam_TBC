package com.example.test7.presenter.screen.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.usecase.RegisterUserUseCase
import com.example.test7.presenter.event.register.RegisterEvent
import com.example.test7.presenter.model.UserAuthenticator
import com.example.test7.presenter.state.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
):ViewModel() {

    private val _navigationEventFlow = MutableSharedFlow<NavigationEvent>()
    val navigationEventFlow get() = _navigationEventFlow

    private val _registrationState = MutableStateFlow(RegisterState())
    val registrationState = _registrationState

    fun onEvent(event:RegisterEvent){
        when(event){
            is RegisterEvent.RegisterUser -> {
                registerUser(event.email,event.password)
            }

            is RegisterEvent.MoveBackToLogin -> {
                giveNavigationFlowNavigationEvent(event.email,event.password)
            }

            is RegisterEvent.ResetErrorMessage -> {
                resetErrorMessageToNull()
            }
        }
    }

    private fun resetErrorMessageToNull(){
        viewModelScope.launch {
            _registrationState.update {
                it.copy(errorMessage = null)
            }
        }

    }

    private fun registerUser(email:String,password:String){
        viewModelScope.launch {
            registerUserUseCase(email,password).collect{result ->
                when(result){
                    is ResultWrapper.Error -> {
                        _registrationState.update {
                            it.copy(errorMessage = result.errorMessage)
                        }
                    }
                    is ResultWrapper.Loading -> {
                        _registrationState.update {
                            it.copy(isLoading = result.loading)
                        }
                    }
                    is ResultWrapper.Success -> {
                        _registrationState.update {
                            it.copy(userAuthenticator = UserAuthenticator(email,password))
                        }
                    }
                }
            }
        }

    }

    private fun giveNavigationFlowNavigationEvent(email: String,password: String){
        viewModelScope.launch {
            _navigationEventFlow.emit(NavigationEvent.NavigateBackToLoginPage(email,password))
        }
    }

    sealed interface NavigationEvent{
        data class NavigateBackToLoginPage(val email:String, val password:String):NavigationEvent
    }
}