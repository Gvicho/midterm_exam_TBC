package com.example.test7.presenter.screen.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test7.domain.usecase.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor( private val getTokenUseCase: GetTokenUseCase):ViewModel() {

    private val _navigationEvent = MutableSharedFlow<SplashNavigationEvent>()
    val navigationEvent: SharedFlow<SplashNavigationEvent> get() = _navigationEvent

    fun navigateToNextScreen(){
        viewModelScope.launch {
            val splashDelayInMillis = 1500L
            delay(splashDelayInMillis)
            getTokenUseCase().collect{token->
                Log.d("tag123", "ViewModel_collect")
                if(token.isEmpty()){
                    _navigationEvent.emit(SplashNavigationEvent.NavigateToLogin)
                }else{
                    _navigationEvent.emit(SplashNavigationEvent.NavigateToHome)
                }
            }
        }

    }

    sealed interface SplashNavigationEvent{
        data object NavigateToLogin :SplashNavigationEvent
        data object NavigateToHome :SplashNavigationEvent
    }

}

