package com.plz.no.anr.lol.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.plz.no.anr.lol.ui.feature.summoner.SummonerContract
import com.plz.no.anr.lol.ui.feature.summoner.SummonerViewModel
import com.plz.no.anr.lol.ui.feature.summoner.composables.SummonerScreen
import com.plz.no.anr.lol.ui.navigation.navigateToSpectator

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
                is SummonerContract.SideEffect.Navigation.Back -> navController.popBackStack()
                is SummonerContract.SideEffect.Navigation.ToSpectator -> navController.navigateToSpectator(it.name)
            }
        }
    )
}