package com.plznoanr.lol.feature.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val SettingRoute = "setting_route"

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    navigate(SettingRoute, navOptions)
}