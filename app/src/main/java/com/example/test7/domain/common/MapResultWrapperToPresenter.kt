package com.example.homework18.domain.common

import com.example.test6.data.common.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <Entity:Any,Presenter> Flow<ResultWrapper<Entity>>.mapResultWrapper(mapper: (Entity)->Presenter) : Flow<ResultWrapper<Presenter>>{
    return this.map {entityResult ->
        when(entityResult) {
            is ResultWrapper.Success -> {
                ResultWrapper.Success(data = mapper(entityResult.data!!))
            }
            is ResultWrapper.Error -> {
                ResultWrapper.Error(errorMessage = entityResult.errorMessage!!)
            }
            is ResultWrapper.Loading -> {
                ResultWrapper.Loading(loading = entityResult.loading)
            }
        }
    }
}