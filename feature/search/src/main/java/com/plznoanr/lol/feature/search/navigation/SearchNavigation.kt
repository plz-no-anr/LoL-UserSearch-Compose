package com.plznoanr.lol.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plznoanr.lol.feature.search.SearchRoute
import com.plznoanr.lol.feature.search.ShowSnackbar

const val SearchGraph = "search_graph"
const val SearchRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(SearchRoute, navOptions)
}

fun NavGraphBuilder.searchGraph(
    onShowSnackbar: suspend  (String) -> Boolean,
    navigateToSummoner: (String, String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = SearchGraph,
        startDestination = SearchRoute,
    ) {
        composable(route = SearchRoute) {
            SearchRoute(
                onShowSnackbar = onShowSnackbar,
                navigateToSummoner = navigateToSummoner
            )
        }
        nestedGraphs()
    }

}