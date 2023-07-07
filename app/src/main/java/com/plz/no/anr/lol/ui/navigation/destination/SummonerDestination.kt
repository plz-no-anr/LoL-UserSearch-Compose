package com.plz.no.anr.lol.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plz.no.anr.lol.ui.feature.summoner.SummonerContract
import com.plz.no.anr.lol.ui.feature.summoner.SummonerViewModel
import com.plz.no.anr.lol.ui.feature.summoner.composables.SummonerScreen
import com.plz.no.anr.lol.ui.navigation.navigateToSpectator

@Composable
fun SummonerDestination(
    viewModel: SummonerViewModel = hiltViewModel(),
    navController: NavController
) {
    SummonerScreen(
        state = viewModel.state.value,
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