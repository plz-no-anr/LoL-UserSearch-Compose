package com.plznoanr.lol.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.plznoanr.lol.feature.bookmark.navigation.bookmarkScreen
import com.plznoanr.lol.feature.home.navigation.HomeRoute
import com.plznoanr.lol.feature.home.navigation.homeScreen
import com.plznoanr.lol.feature.search.navigation.searchGraph
import com.plznoanr.lol.feature.setting.navigation.settingScreen
import com.plznoanr.lol.feature.spectator.navigation.navigateToSpectator
import com.plznoanr.lol.feature.summoner.navigation.navigateToSummoner
import com.plznoanr.lol.feature.summoner.navigation.summonerScreen
import kotlinx.coroutines.flow.Flow


@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = HomeRoute,
    navigateCallbackFlow: (TopDestination) -> Flow<Boolean>
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(
            navCallbackFlow = { navigateCallbackFlow(TopDestination.Home) }
        )

        searchGraph(
            navigateToSummoner = navController::navigateToSummoner,
            nestedGraphs = {
                summonerScreen(
                    onBackPressed = navController::popBackStack,
                    navigateToSpectator = navController::navigateToSpectator
                )
            }
        )

        bookmarkScreen()

        settingScreen()

    }

}
