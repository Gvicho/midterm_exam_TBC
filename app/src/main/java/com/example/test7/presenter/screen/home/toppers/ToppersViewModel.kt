package com.example.test7.presenter.screen.home.toppers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.usecase.GetTopWorkersUseCase
import com.example.test7.domain.usecase.GetWorkersListUseCase
import com.example.test7.presenter.event.home.toppers.ToppersEvent
import com.example.test7.presenter.mapper.toPresenter
import com.example.test7.presenter.state.home.ToppersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToppersViewModel @Inject constructor(
    val getTopWorkersUseCase: GetTopWorkersUseCase
):ViewModel() {

    private val _uiStateFlow = MutableStateFlow(ToppersState())
    val uiStateFlow : StateFlow<ToppersState> = _uiStateFlow

    private val _navigationEventsFlow = MutableSharedFlow<ToppersNavigationEvents>()
    val navigationEventsFlow: SharedFlow<ToppersNavigationEvents> get() = _navigationEventsFlow

    init {
        loadTopWorkers()
    }

    fun onEvent(event:ToppersEvent){
        when(event){
            is ToppersEvent.OpenWorkersDetails ->{

            }

            is ToppersEvent.ResetErrorMessageToNull -> {
                resetErrorMessageToNull()
            }
        }
    }

    private fun loadTopWorkers(){
        viewModelScope.launch {
            getTopWorkersUseCase().collect{result->
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

    sealed interface ToppersNavigationEvents{
        data class NavigateToWorkerDetailsFragment(val id:Int):ToppersNavigationEvents
    }
}