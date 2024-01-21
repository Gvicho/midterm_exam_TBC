package com.example.test7.data.service

import com.example.test7.data.model.TopWorkersDto
import com.example.test7.data.model.WorkerDto
import com.example.test7.data.model.WorkersListPageDto
import com.example.test7.data.model.details.WorkerDetailsPageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface WorkersListService {
    @GET("Workers")
    suspend fun getWorkersList(): Response<WorkersListPageDto>

    @GET("TopRaters")
    suspend fun getTopWorkersList(): Response<TopWorkersDto>

    @GET("MyAccount")
    suspend fun getProfileInfo(): Response<WorkerDto>

    @GET("WorkerDetails/{id}")
    suspend fun getWorkerDetails(@Path("id")id:Int):Response<WorkerDetailsPageDto>
}