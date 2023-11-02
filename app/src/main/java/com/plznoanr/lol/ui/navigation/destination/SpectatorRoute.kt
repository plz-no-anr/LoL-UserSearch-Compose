package com.plznoanr.lol.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.plznoanr.lol.ui.feature.spectator.SpectatorSideEffect
import com.plznoanr.lol.ui.feature.spectator.SpectatorViewModel
import com.plznoanr.lol.ui.feature.spectator.composables.SpectatorScreen

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
            if (it is SpectatorSideEffect.Navigation.Back) {
                navController.popBackStack()
            }
        },
    )

}

@Preview
@Composable
fun SpectatorDestinationPreview() {

}