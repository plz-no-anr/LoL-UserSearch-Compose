package com.plz.no.anr.lol_usersearch_compose.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator.SpectatorContract
import com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator.SpectatorViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator.composables.SpectatorScreen

@Composable
fun SpectatorDestination(
    viewModel: SpectatorViewModel = hiltViewModel(),
    navController: NavController
) {
    SpectatorScreen(
        state = viewModel.uiState.value,
        effectFlow = viewModel.effect,
        onEvent = viewModel::setEvent,
        onNavigationRequested = {
            if (it is SpectatorContract.Effect.Navigation.Back) {
                navController.popBackStack()
            }
        },
    )

}

@Preview
@Composable
fun SpectatorDestinationPreview() {

}