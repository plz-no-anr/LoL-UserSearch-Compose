package com.plznoanr.lol.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.plznoanr.lol.ui.feature.summoner.SummonerSideEffect
import com.plznoanr.lol.ui.feature.summoner.SummonerViewModel
import com.plznoanr.lol.ui.feature.summoner.composables.SummonerScreen
import com.plznoanr.lol.ui.navigation.navigateToSpectator

@Composable
fun SummonerRoute(
    viewModel: SummonerViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SummonerScreen(
        state = state,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::postIntent,
        onNavigationRequested = {
            when (it) {
                is SummonerSideEffect.Navigation.Back -> navController.popBackStack()
                is SummonerSideEffect.Navigation.ToSpectator -> navController.navigateToSpectator(it.summonerId)
            }
        }
    )
}