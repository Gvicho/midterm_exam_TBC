package com.example.test7.presenter.mapper

import com.example.test7.domain.model.WorkerEntity
import com.example.test7.presenter.model.WorkerPresenter

fun WorkerEntity.toPresenter():WorkerPresenter{
    return WorkerPresenter(
        avatar = avatar,
        name = name,
        profession = profession,
        rate = rate,
        averageHoursWorked = averageHoursWorked,
    )
}