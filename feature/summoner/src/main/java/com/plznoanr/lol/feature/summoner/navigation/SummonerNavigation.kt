package com.plznoanr.lol.feature.summoner.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.plznoanr.lol.core.navigation.NavGraph
import com.plznoanr.lol.feature.summoner.SummonerRoute

fun NavController.navigateToSummoner(summonerName: String, summonerTag: String) {
    navigate(NavGraph.SummonerRoute(summonerName, summonerTag)) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.summonerScreen(
    navigateToSpectator: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    composable<NavGraph.SummonerRoute> {
        val summonerName = it.toRoute<NavGraph.SummonerRoute>().summonerName
        val summonerTag = it.toRoute<NavGraph.SummonerRoute>().summonerTag

        SummonerRoute(
            navigateToSpectator = navigateToSpectator,
            onBackPress = onBackPressed
        )
    }

}