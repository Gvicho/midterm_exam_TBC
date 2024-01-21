package com.example.test7.presenter.screen.home.workers

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.usecase.GetWorkersListUseCase
import com.example.test7.presenter.event.home.workers_list.WorkersListEvent
import com.example.test7.presenter.mapper.toPresenter
import com.example.test7.presenter.state.home.WorkersListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkersListViewModel @Inject constructor(
    private val getWorkersListUseCase: GetWorkersListUseCase
):ViewModel() {

    private val _uiStateFlow = MutableStateFlow(WorkersListState())
    val uiStateFlow :StateFlow<WorkersListState> = _uiStateFlow

    private val _eventsFlow = MutableSharedFlow<WorkersListEvent>()
    val eventsFlow: SharedFlow<WorkersListEvent> get() = _eventsFlow

    init {
        loadWorkersList()
        d("tag123","viewModel init")
    }

    fun onEvent(event:WorkersListEvent){
        when(event){
            is WorkersListEvent.OpenWorkersDetails -> {

            }

            is WorkersListEvent.FilterWorkersListByProfession -> {

            }

            is WorkersListEvent.ResetErrorMessageToNull -> {
                resetErrorMessageToNull()
            }
        }
    }

    private fun loadWorkersList(){
        viewModelScope.launch {
            getWorkersListUseCase().collect{result->
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