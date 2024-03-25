package com.plznoanr.lol.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.plznoanr.lol.feature.home.HomeRoute
import kotlinx.coroutines.flow.Flow

const val HomeRoute = "home_route"

fun NavGraphBuilder.homeScreen(
    navCallbackFlow: () -> Flow<Boolean>
) {
    composable(route = HomeRoute) {
        HomeRoute(
            navCallbackFlow = navCallbackFlow
        )
    }
}