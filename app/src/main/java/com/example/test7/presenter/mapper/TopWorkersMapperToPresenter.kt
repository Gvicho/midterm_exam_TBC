package com.example.test7.presenter.mapper

import com.example.test7.domain.model.TopWorkers
import com.example.test7.presenter.model.WorkerPresenter

fun TopWorkers.toPresenter():List<WorkerPresenter>{
    return topWorkersList.map {
        it.toPresenter()
    }
}