package com.plznoanr.lol.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val SearchRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(SearchRoute, navOptions)
}