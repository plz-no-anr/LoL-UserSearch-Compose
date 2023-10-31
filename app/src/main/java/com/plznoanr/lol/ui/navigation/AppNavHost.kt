package com.plznoanr.lol.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plznoanr.lol.ui.navigation.destination.HomeRoute
import com.plznoanr.lol.ui.navigation.destination.SearchRoute
import com.plznoanr.lol.ui.navigation.destination.SpectatorRoute
import com.plznoanr.lol.ui.navigation.destination.SummonerRoute


@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.Main.route,
    ) {
        homeNavGraph(navController = navController)

        searchGraph(navController = navController)

        summonerGraph(navController = navController)

        spectatorGraph(navController = navController)
    }

}

private fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
) {
    composable(route = Destination.Main.route) {
        HomeRoute(navController = navController)
    }
}

private fun NavGraphBuilder.searchGraph(
    navController: NavController,
) {
    composable(route = Destination.Search.route) {
        SearchRoute(navController = navController)
    }
}

private fun NavGraphBuilder.summonerGraph(
    navController: NavController,
) {
    composable(
        route = Destination.Summoner.route,
        arguments = listOf(navArgument(name = Destination.Summoner.Args.KEY_SUMMONER_NAME) {
            type = NavType.StringType
        })
    ) {
        SummonerRoute(navController = navController)
    }
}

private fun NavGraphBuilder.spectatorGraph(
    navController: NavController,
) {
    composable(
        route = Destination.Spectator.route,
        arguments = listOf(navArgument(name = Destination.Spectator.Args.KEY_SUMMONER_ID) {
            type = NavType.StringType
        })
    ) {
        SpectatorRoute(navController = navController)
    }
}
