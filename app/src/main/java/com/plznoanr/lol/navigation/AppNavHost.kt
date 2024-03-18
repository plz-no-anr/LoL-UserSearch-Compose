package com.plznoanr.lol.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map


@Composable
fun AppNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend  (String) -> Boolean,
    startDestination: String = HomeRoute,
    navigateCallbackFlow: Flow<TopDestination>
) {

    val navCallback = remember {
        { topDestination: TopDestination -> navigateCallbackFlow.map { it == topDestination }.filter { it } }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(
            navCallbackFlow = navCallback(TopDestination.Home)
        )

        searchGraph(
            onShowSnackbar = onShowSnackbar,
            navigateToSummoner = navController::navigateToSummoner,
            nestedGraphs = {
                summonerScreen(
                    onShowSnackbar = onShowSnackbar,
                    onBackPressed = navController::popBackStack,
                    navigateToSpectator = navController::navigateToSpectator
                )
            }
        )

        bookmarkScreen()

        settingScreen(
            onShowSnackbar = onShowSnackbar
        )

    }

}
