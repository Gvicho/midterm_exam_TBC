package com.example.test7.presenter.screen.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.usecase.GetProfileInfoUseCase
import com.example.test7.presenter.event.home.profile.ProfileEvent
import com.example.test7.presenter.event.home.toppers.ToppersEvent
import com.example.test7.presenter.mapper.toPresenter
import com.example.test7.presenter.state.home.ProfileState
import com.example.test7.presenter.state.home.ToppersState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase
):ViewModel() {

    private val _uiStateFlow = MutableStateFlow(ProfileState())
    val uiStateFlow : StateFlow<ProfileState> = _uiStateFlow

    init {
        loadProfileInfo()
    }

    fun onEvent(event: ProfileEvent){
        when(event){
            is ProfileEvent.ResetErrorMessageToNull -> {
                resetErrorMessageToNull()
            }
        }
    }

    private fun loadProfileInfo(){
        viewModelScope.launch {
            getProfileInfoUseCase().collect{result->
                when(result){
                    is ResultWrapper.Error -> {
                        _uiStateFlow.update {
                            it.copy(errorMessage = result.errorMessage)
                        }
                    }
                    is ResultWrapper.Loading -> {
                        _uiStateFlow.update {
                            it.copy(isLoading = result.loading)
                        }
                    }
                    is ResultWrapper.Success -> {
                        _uiStateFlow.update {
                            it.copy(isSuccess = result.data!!.toPresenter())
                        }
                    }
                }
            }
        }
    }

    private fun resetErrorMessageToNull(){
        _uiStateFlow.update {
            it.copy(errorMessage = null)
        }
    }
}