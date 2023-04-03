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
        startDestination = Navigation.Routes.MAIN,
    ) {
        mainDestination(navController = navController)

        searchDestination(navController = navController)

        summonerDestination(navController = navController)

        spectatorDestination(navController = navController)

    }

}

object Navigation {
    object Args {
        const val SUMMONER_NAME = "summoner_name"
    }

    object Routes {
        const val MAIN = "main"
        const val SEARCH = "search"
        const val SUMMONER = "summoner/{${Args.SUMMONER_NAME}}"
        const val SPECTATOR = "spectator/{${Args.SUMMONER_NAME}}"
    }
}


fun NavGraphBuilder.mainDestination(
    navController: NavController,
) {
    composable(route = Navigation.Routes.MAIN) {
        MainDestination(navController = navController)
    }
}

fun NavGraphBuilder.searchDestination(
    navController: NavController,
) {
    composable(route = Navigation.Routes.SEARCH) {
        SearchDestination(navController = navController)
    }
}

fun NavGraphBuilder.summonerDestination(
    navController: NavController,
) {
    composable(
        route = Navigation.Routes.SUMMONER,
        arguments = listOf(navArgument(name = Navigation.Args.SUMMONER_NAME) {
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
        route = Navigation.Routes.SPECTATOR,
        arguments = listOf(navArgument(name = Navigation.Args.SUMMONER_NAME) {
            type = NavType.StringType
        })
    ) {
        SpectatorDestination(navController = navController)
    }
}

fun NavController.navigateToMain() {
    navigate(route = Navigation.Routes.MAIN)
}

fun NavController.navigateToSearch() {
    navigate(route = Navigation.Routes.SEARCH)
}

fun NavController.navigateToSpectator(name: String) {
    navigate(route = "spectator/$name")
}

fun NavController.navigateToSummoner(name: String) {
    navigate(route = "summoner/$name")
}