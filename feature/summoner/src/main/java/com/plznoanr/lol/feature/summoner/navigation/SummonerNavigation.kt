package com.plznoanr.lol.feature.summoner.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.plznoanr.lol.feature.summoner.SummonerRoute

const val SummonerRoute = "summoner_route"
const val SummonerNameArg = "summonerName"
const val SummonerTagArg = "summonerTag"

internal class SummonerArgs(
    val summonerName: String,
    val summonerTag: String
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        summonerName = checkNotNull(savedStateHandle[SummonerNameArg]),
        summonerTag = checkNotNull(savedStateHandle[SummonerTagArg])
    )
}

fun NavController.navigateToSummoner(summonerName: String, summonerTag: String) {
    navigate("$SummonerRoute?$summonerName/$summonerTag") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.summonerScreen(
    onShowSnackbar: suspend (String) -> Boolean,
    navigateToSpectator: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    composable(
        route = "$SummonerRoute?{$SummonerNameArg}/{$SummonerTagArg}",
        arguments = listOf(
            navArgument(SummonerNameArg) { type = NavType.StringType },
            navArgument(SummonerTagArg) { type = NavType.StringType },
        )
    ) {
        SummonerRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToSpectator = navigateToSpectator,
            onBackPress = onBackPressed
        )
    }

}