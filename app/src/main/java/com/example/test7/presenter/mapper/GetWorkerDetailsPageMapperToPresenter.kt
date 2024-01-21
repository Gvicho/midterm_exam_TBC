package com.example.test7.presenter.mapper

import com.example.test7.domain.model.GetWorkerDetailsPage
import com.example.test7.presenter.model.worker_details.ChartDataList
import com.example.test7.presenter.model.worker_details.WorkerDetailsListObject
import com.example.test7.presenter.model.worker_details.WorkerDetailsObjectType

fun GetWorkerDetailsPage.toPresenter(): List<WorkerDetailsListObject> {
    val detailsObject1 = WorkerDetailsListObject(
        workerInfo = workersInfo.toPresenter(),
        rateData = null,
        lastWorkDate = null,
        itemType = WorkerDetailsObjectType.INFO
    )

    val sizeOfRateData = monthRateData?.size
    val monthList = if (sizeOfRateData != null) createFloatList(sizeOfRateData) else null

    val detailsObject2 = WorkerDetailsListObject(
        workerInfo = null,
        rateData = ChartDataList(
            monthList = monthList,
            ratingList = monthRateData
        ),
        lastWorkDate = null,
        itemType = WorkerDetailsObjectType.CHART
    )

    val detailsObject3 = WorkerDetailsListObject(
        workerInfo = null,
        rateData = null,
        lastWorkDate = lastWorkMonthData.toPresenter(),
        itemType = WorkerDetailsObjectType.DATE
    )

    return listOf(detailsObject1, detailsObject2, detailsObject3)
}

fun createFloatList(n: Int): List<Float> {
    return (1..n).map { it.toFloat() }
}