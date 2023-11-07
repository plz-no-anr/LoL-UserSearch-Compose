package com.plznoanr.lol.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plznoanr.lol.feature.search.SearchRoute

const val SearchGraph = "search_graph"
const val SearchRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(SearchRoute, navOptions)
}

fun NavGraphBuilder.searchGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = SearchGraph,
        startDestination = SearchRoute,
    ) {
        composable(route = SearchRoute) {
            SearchRoute(
            )
        }
        nestedGraphs()
    }

}