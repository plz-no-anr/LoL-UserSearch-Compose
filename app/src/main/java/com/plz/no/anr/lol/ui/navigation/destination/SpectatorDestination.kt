package com.plz.no.anr.lol.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plz.no.anr.lol.ui.feature.spectator.SpectatorContract
import com.plz.no.anr.lol.ui.feature.spectator.SpectatorViewModel
import com.plz.no.anr.lol.ui.feature.spectator.composables.SpectatorScreen

@Composable
fun SpectatorDestination(
    viewModel: SpectatorViewModel = hiltViewModel(),
    navController: NavController
) {
    SpectatorScreen(
        state = viewModel.state.value,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::postIntent,
        onNavigationRequested = {
            if (it is SpectatorContract.SideEffect.Navigation.Back) {
                navController.popBackStack()
            }
        },
    )

}

@Preview
@Composable
fun SpectatorDestinationPreview() {

}