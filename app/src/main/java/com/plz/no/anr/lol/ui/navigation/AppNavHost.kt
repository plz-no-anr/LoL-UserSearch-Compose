package com.plz.no.anr.lol.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plz.no.anr.lol.ui.navigation.destination.HomeRoute
import com.plz.no.anr.lol.ui.navigation.destination.SearchDestination
import com.plz.no.anr.lol.ui.navigation.destination.SpectatorRoute
import com.plz.no.anr.lol.ui.navigation.destination.SummonerRoute

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.Main.route,
    ) {
        mainScreen(navController = navController)

        searchScreen(navController = navController)

        summonerScreen(navController = navController)

        spectatorScreen(navController = navController)
    }

}

private fun NavGraphBuilder.mainScreen(
    navController: NavController,
) {
    composable(route = Destination.Main.route) {
        HomeRoute(navController = navController)
    }
}

private fun NavGraphBuilder.searchScreen(
    navController: NavController,
) {
    composable(route = Destination.Search.route) {
        SearchDestination(navController = navController)
    }
}

private fun NavGraphBuilder.summonerScreen(
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

private fun NavGraphBuilder.spectatorScreen(
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
