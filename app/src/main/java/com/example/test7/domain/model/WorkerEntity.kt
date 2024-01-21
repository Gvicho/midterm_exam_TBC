package com.example.test7.domain.model

data class WorkerEntity(
    val id:Int,
    val avatar:String? = null,
    val name:String,
    val profession:String,
    val rate:Double? = null,
    val averageHoursWorked:Double
) {
}