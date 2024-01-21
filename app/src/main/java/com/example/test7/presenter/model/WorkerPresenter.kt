package com.example.test7.presenter.model

data class WorkerPresenter(
    val avatar:String? = null,
    val name:String,
    val profession:String,
    val rate:Double? = null,
    val averageHoursWorked:Double
) {
}