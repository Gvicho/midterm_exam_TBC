package com.example.test7.presenter.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test7.domain.usecase.ClearTokenUseCase
import com.example.test7.presenter.event.home.HomeEvent
import com.example.test7.presenter.screen.splash.SplashViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val clearTokenUseCase: ClearTokenUseCase
):ViewModel() {

    private val _navigationEvent = MutableSharedFlow<HomeNavigationEvent>()
    val navigationEvent: SharedFlow<HomeNavigationEvent> get() = _navigationEvent

    fun onEvent(event:HomeEvent){
        when(event){
            HomeEvent.LogOutAndClearToken -> {
                logoutUserDeleteToken()
                setToLoginPageNavigationEvent()
            }
        }
    }

    private fun logoutUserDeleteToken(){
        viewModelScope.launch {
            clearTokenUseCase()
        }
    }

    private fun setToLoginPageNavigationEvent(){
        viewModelScope.launch {
            _navigationEvent.emit(HomeNavigationEvent.NavigateToLogin)
        }
    }


    sealed interface HomeNavigationEvent{
        data object NavigateToLogin :HomeNavigationEvent
    }
}