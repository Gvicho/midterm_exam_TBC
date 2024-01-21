package com.example.test7.presenter.mapper

import com.example.test7.domain.model.GetLastWorkDate
import com.example.test7.presenter.model.worker_details.WorkingTimeDetails

fun GetLastWorkDate.toPresenter():WorkingTimeDetails{
    return WorkingTimeDetails(
        lastYear =lastYear,
        lastMonth=lastMonth,
        status=status
    )
}