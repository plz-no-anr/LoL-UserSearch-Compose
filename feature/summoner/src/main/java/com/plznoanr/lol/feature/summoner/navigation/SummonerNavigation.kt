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

internal class SummonerArgs(
    val summonerName: String
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        summonerName = checkNotNull(savedStateHandle[SummonerNameArg])
    )
}

fun NavController.navigateToSummoner(summonerName: String) {
    navigate("$SummonerRoute?$summonerName") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.summonerScreen(
    onBackPressed: () -> Unit,
) {
    composable(
        route = "$SummonerRoute?$SummonerNameArg",
        arguments = listOf(
            navArgument(SummonerNameArg) { type = NavType.StringType }
        )
    ) {
        SummonerRoute()
    }

}