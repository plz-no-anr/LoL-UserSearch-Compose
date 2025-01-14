package com.plznoanr.lol.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.plznoanr.lol.core.navigation.NavGraph
import com.plznoanr.lol.feature.home.HomeRoute
import kotlinx.coroutines.flow.Flow

fun NavGraphBuilder.homeScreen(
    navCallbackFlow: () -> Flow<Boolean>
) {
    composable<NavGraph.HomeRoute> {
        HomeRoute(
            navCallbackFlow = navCallbackFlow
        )
    }
}