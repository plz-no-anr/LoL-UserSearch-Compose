package com.plznoanr.lol.feature.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.plznoanr.lol.feature.setting.SettingRoute

const val SettingRoute = "setting_route"

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    navigate(SettingRoute, navOptions)
}

fun NavGraphBuilder.settingScreen(
    onShowSnackbar: suspend (String) -> Boolean
) {
    composable(route = SettingRoute) {
        SettingRoute(
            onShowSnackbar = onShowSnackbar
        )
    }
}