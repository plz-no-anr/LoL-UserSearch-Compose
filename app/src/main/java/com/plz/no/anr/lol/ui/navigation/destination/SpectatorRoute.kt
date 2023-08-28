package com.plz.no.anr.lol.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.plz.no.anr.lol.ui.feature.spectator.SpectatorContract
import com.plz.no.anr.lol.ui.feature.spectator.SpectatorViewModel
import com.plz.no.anr.lol.ui.feature.spectator.composables.SpectatorScreen

@Composable
fun SpectatorRoute(
    viewModel: SpectatorViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SpectatorScreen(
        state = state,
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