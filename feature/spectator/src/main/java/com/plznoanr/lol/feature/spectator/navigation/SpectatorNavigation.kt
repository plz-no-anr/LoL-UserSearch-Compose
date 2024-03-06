package com.plznoanr.lol.feature.spectator.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.plznoanr.lol.feature.spectator.SpectatorRoute

const val SpectatorRoute = "spectator_route"
const val SummonerIdArg = "summonerId"

fun NavController.navigateToSpectator(navOptions: NavOptions? = null) {
    navigate(SpectatorRoute, navOptions)
}

internal class SpectatorArgs(
    val summonerId: String
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        summonerId = checkNotNull(savedStateHandle[SummonerIdArg])
    )
}

fun NavController.navigateToSpectator(summonerId: String) {
    navigate("$SpectatorRoute?$summonerId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.spectatorScreen(
    onBackPressed: () -> Unit,
) {
    composable(
        route = "$SpectatorRoute?{$SummonerIdArg}",
        arguments = listOf(
            navArgument(SummonerIdArg) { type = NavType.StringType }
        )
    ) {
        SpectatorRoute()
    }

}