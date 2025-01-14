package com.plznoanr.lol.feature.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.plznoanr.lol.core.navigation.NavGraph
import com.plznoanr.lol.feature.setting.SettingRoute

fun NavGraphBuilder.settingScreen(
) {
    composable<NavGraph.SettingRoute> {
        SettingRoute()
    }
}