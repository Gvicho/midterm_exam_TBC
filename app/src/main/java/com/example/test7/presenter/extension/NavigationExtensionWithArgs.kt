package com.example.test7.presenter.extension

import android.os.Bundle
import androidx.navigation.NavController

fun NavController.safeNavigateWithArguments(actionId:Int,bundle: Bundle) {
    currentDestination?.getAction(actionId)?.run {
        navigate(actionId,bundle)
    }
}