package com.example.test7.presenter.model.workers_list

import com.example.test7.presenter.model.WorkerPresenter

data class WorkersListObjectPresenter(
    val id:Int,
    val type:ItemType,
    val professionsList: List<String>? =null,
    val worker:WorkerPresenter?=null
) {
}

enum class ItemType(){
    PROFESSIONS_RECYCLER,
    WORKER_ITEM
}