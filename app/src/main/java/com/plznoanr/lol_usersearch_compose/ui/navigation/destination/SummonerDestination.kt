package com.plznoanr.lol_usersearch_compose.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plznoanr.lol_usersearch_compose.ui.feature.summoner.SummonerContract
import com.plznoanr.lol_usersearch_compose.ui.feature.summoner.SummonerViewModel
import com.plznoanr.lol_usersearch_compose.ui.feature.summoner.composables.SummonerScreen
import com.plznoanr.lol_usersearch_compose.ui.navigation.navigateToSpectator

@Composable
fun SummonerDestination(
    viewModel: SummonerViewModel = hiltViewModel(),
    navController: NavController
) {
    SummonerScreen(
        state = viewModel.state.value,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::setIntent,
        onNavigationRequested = {
            when (it) {
                is SummonerContract.SideEffect.Navigation.Back -> navController.popBackStack()
                is SummonerContract.SideEffect.Navigation.ToSpectator -> navController.navigateToSpectator(it.name)
            }
        }
    )
}