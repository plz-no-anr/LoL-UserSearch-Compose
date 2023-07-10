package com.plz.no.anr.lol.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plz.no.anr.lol.ui.navigation.destination.MainDestination
import com.plz.no.anr.lol.ui.navigation.destination.SearchDestination
import com.plz.no.anr.lol.ui.navigation.destination.SpectatorDestination
import com.plz.no.anr.lol.ui.navigation.destination.SummonerDestination

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.Main.route,
    ) {
        mainDestination(navController = navController)

        searchDestination(navController = navController)

        summonerDestination(navController = navController)

        spectatorDestination(navController = navController)
    }

}

private fun NavGraphBuilder.mainDestination(
    navController: NavController,
) {
    composable(route = Destination.Main.route) {
        MainDestination(navController = navController)
    }
}

private fun NavGraphBuilder.searchDestination(
    navController: NavController,
) {
    composable(route = Destination.Search.route) {
        SearchDestination(navController = navController)
    }
}

private fun NavGraphBuilder.summonerDestination(
    navController: NavController,
) {
    composable(
        route = Destination.Summoner.route,
        arguments = listOf(navArgument(name = Destination.Summoner.Args.KEY_SUMMONER_NAME) {
            type = NavType.StringType
        })
    ) {
        SummonerDestination(navController = navController)
    }
}

private fun NavGraphBuilder.spectatorDestination(
    navController: NavController,
) {
    composable(
        route = Destination.Spectator.route,
        arguments = listOf(navArgument(name = Destination.Spectator.Args.KEY_SUMMONER_NAME) {
            type = NavType.StringType
        })
    ) {
        SpectatorDestination(navController = navController)
    }
}
