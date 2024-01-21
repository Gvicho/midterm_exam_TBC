package com.example.test7.presenter.screen.employ_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.data.common.ResultWrapper
import com.example.test7.domain.usecase.GetWorkersDetailsUseCase
import com.example.test7.presenter.event.details.WorkerDetailsEvent
import com.example.test7.presenter.mapper.toPresenter
import com.example.test7.presenter.state.details.WorkersDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployDetailsViewModel @Inject constructor(
    private val getUsersDetails:GetWorkersDetailsUseCase
):ViewModel() {

    private val _uiStateFlow = MutableStateFlow(WorkersDetailsState())
    val uiStateFlow : StateFlow<WorkersDetailsState> = _uiStateFlow

    fun onEvent(event:WorkerDetailsEvent){
        when(event){
            is WorkerDetailsEvent.LoadUserDetails -> {
                loadWorkersDetails(event.id)
            }

            WorkerDetailsEvent.ResetErrorMessageToNull -> {
                resetErrorMessageToNull()
            }
        }
    }

    private fun loadWorkersDetails(id:Int){
        viewModelScope.launch {
            getUsersDetails(id).collect{result->
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

