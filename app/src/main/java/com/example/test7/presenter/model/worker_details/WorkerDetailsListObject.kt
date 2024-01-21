package com.example.test7.presenter.model.worker_details

import com.example.test7.presenter.model.WorkerPresenter

class WorkerDetailsListObject (
    val workerInfo:WorkerPresenter? = null,
    val rateData:ChartDataList?= null,
    val lastWorkDate:WorkingTimeDetails?=null,
    val itemType:WorkerDetailsObjectType
)

enum class WorkerDetailsObjectType(){
    CHART,
    DATE,
    INFO
}