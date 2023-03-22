package com.plz.no.anr.lol_usersearch_compose.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plz.no.anr.lol_usersearch_compose.ui.feature.summoner.SummonerContract
import com.plz.no.anr.lol_usersearch_compose.ui.feature.summoner.SummonerViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.feature.summoner.composables.SummonerScreen

@Composable
fun SummonerDestination(
    viewModel: SummonerViewModel = hiltViewModel(),
    navController: NavController
) {
    SummonerScreen(
        state = viewModel.uiState.value,
        effectFlow = viewModel.effect,
        onEvent = viewModel::setEvent,
        onNavigationRequested = {
            when (it) {
                is SummonerContract.Effect.Navigation.Back -> navController.popBackStack()
            }
        }
    )
}