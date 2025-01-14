package com.plznoanr.lol.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.plznoanr.lol.core.navigation.NavGraph
import com.plznoanr.lol.core.navigation.TopDestination
import com.plznoanr.lol.feature.bookmark.navigation.bookmarkScreen
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
    startDestination: NavGraph = NavGraph.HomeRoute,
    navigateCallbackFlow: (TopDestination) -> Flow<Boolean>
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMedium)) +
                    slideInHorizontally(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessMedium,
                            visibilityThreshold = IntOffset.VisibilityThreshold
                        ),
                        initialOffsetX = { it }
                    )
        },
        exitTransition = {
            slideOutHorizontally(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMedium,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                ),
                targetOffsetX = { -it }
            ) + fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMedium))
        },
        popEnterTransition = {
            fadeIn(animationSpec = spring()) + slideInHorizontally(
                animationSpec = spring(),
                initialOffsetX = { -it }
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                animationSpec = spring(),
                targetOffsetX = { it }
            ) + fadeOut(animationSpec = spring())
        }
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
