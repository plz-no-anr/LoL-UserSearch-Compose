package com.plznoanr.lol.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val HomeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HomeRoute, navOptions)
}