package com.plznoanr.lol.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plznoanr.lol.core.navigation.NavGraph
import com.plznoanr.lol.feature.search.SearchRoute

fun NavGraphBuilder.searchGraph(
    navigateToSummoner: (String, String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation<NavGraph.SearchGraph>(
        startDestination = NavGraph.SearchGraph.SearchRoute
    ) {
        composable<NavGraph.SearchGraph.SearchRoute> {
            SearchRoute(
                navigateToSummoner = navigateToSummoner
            )
        }
        nestedGraphs()
    }

}