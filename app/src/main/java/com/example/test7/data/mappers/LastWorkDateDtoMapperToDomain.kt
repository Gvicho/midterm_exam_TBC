package com.example.test7.data.mappers

import com.example.test7.data.model.details.LastWorkDateInfoDto
import com.example.test7.domain.model.GetLastWorkDate

fun LastWorkDateInfoDto.toDomain():GetLastWorkDate{
    return GetLastWorkDate(
        lastMonth = lastMonth,
        lastYear = lastYear,
        status = status
    )
}