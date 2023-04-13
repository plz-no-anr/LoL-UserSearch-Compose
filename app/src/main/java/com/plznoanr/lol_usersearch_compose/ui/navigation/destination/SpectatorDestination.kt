package com.plznoanr.lol_usersearch_compose.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plznoanr.lol_usersearch_compose.ui.feature.spectator.SpectatorContract
import com.plznoanr.lol_usersearch_compose.ui.feature.spectator.SpectatorViewModel
import com.plznoanr.lol_usersearch_compose.ui.feature.spectator.composables.SpectatorScreen

@Composable
fun SpectatorDestination(
    viewModel: SpectatorViewModel = hiltViewModel(),
    navController: NavController
) {
    SpectatorScreen(
        state = viewModel.state.value,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::setIntent,
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