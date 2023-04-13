package com.plz.no.anr.lol_usersearch_compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.destination.MainDestination
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.destination.SearchDestination
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.destination.SpectatorDestination
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.destination.SummonerDestination

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Main.path,
    ) {
        mainDestination(navController = navController)

        searchDestination(navController = navController)

        summonerDestination(navController = navController)

        spectatorDestination(navController = navController)

    }

}

fun NavGraphBuilder.mainDestination(
    navController: NavController,
) {
    composable(route = Route.Main.path) {
        MainDestination(navController = navController)
    }
}

fun NavGraphBuilder.searchDestination(
    navController: NavController,
) {
    composable(route = Route.Search.path) {
        SearchDestination(navController = navController)
    }
}

fun NavGraphBuilder.summonerDestination(
    navController: NavController,
) {
    composable(
        route = Route.Summoner.argsPath,
        arguments = listOf(navArgument(name = Route.Summoner.KEY_SUMMONER_NAME) {
            type = NavType.StringType
        })
    ) {
        SummonerDestination(navController = navController)
    }
}

fun NavGraphBuilder.spectatorDestination(
    navController: NavController,
) {
    composable(
        route = Route.Spectator.argsPath,
        arguments = listOf(navArgument(name = Route.Spectator.KEY_SUMMONER_NAME) {
            type = NavType.StringType
        })
    ) {
        SpectatorDestination(navController = navController)
    }
}

fun NavController.navigateToMain() {
    navigate(route = Route.Main.path)
}

fun NavController.navigateToSearch() {
    navigate(route = Route.Search.path)
}

fun NavController.navigateToSpectator(name: String) {
    navigate(route = Route.Spectator.pathWithArgs(name))
}

fun NavController.navigateToSummoner(name: String) {
    navigate(route = Route.Summoner.pathWithArgs(name))
}