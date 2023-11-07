package com.plznoanr.lol.feature.spectator.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

const val SpectatorRoute = "spectator_route"

const val SummonerIdArg = "summonerId"

internal class SpectatorArgs(
    val summonerId: String
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        summonerId = checkNotNull(savedStateHandle[SummonerIdArg])
    )
}

fun NavController.navigationToSpectator(summonerId: String) {
    navigate("$SpectatorRoute?$summonerId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.spectatorScreen(
    onBackPressed: () -> Unit,
) {


}