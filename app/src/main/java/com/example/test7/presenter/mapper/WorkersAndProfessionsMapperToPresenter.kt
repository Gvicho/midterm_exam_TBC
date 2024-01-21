package com.example.test7.presenter.mapper

import com.example.test7.domain.model.WorkersAndProfessionsEntity
import com.example.test7.presenter.model.workers_list.ItemType
import com.example.test7.presenter.model.workers_list.WorkersListObjectPresenter

fun WorkersAndProfessionsEntity.toPresenter():List<WorkersListObjectPresenter>{
    val workersList = mutableListOf<WorkersListObjectPresenter>()
    workersList += WorkersListObjectPresenter(0,ItemType.PROFESSIONS_RECYCLER,professionsList)

    this.workersList.forEach{
        workersList+= WorkersListObjectPresenter(
            id = it.id,
            ItemType.WORKER_ITEM,
            professionsList = null,
            worker = it.toPresenter()
        )
    }

    return workersList.toList()
}