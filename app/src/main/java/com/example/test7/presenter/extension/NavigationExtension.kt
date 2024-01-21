package com.example.test7.presenter.extension

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigateWithArgs(actionId:Int) {
    currentDestination?.getAction(actionId)?.run {
        navigate(actionId)
    }
}