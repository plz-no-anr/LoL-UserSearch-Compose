package com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator.SpectatorContract
import kotlinx.coroutines.flow.Flow

@Composable
fun SpectatorScreen(
    state: SpectatorContract.UiState,
    effectFlow: Flow<SpectatorContract.Effect>?,
    onEvent: (SpectatorContract.Event) -> Unit,
    onNavigationRequested: (SpectatorContract.Effect.Navigation) -> Unit,
) {

}


@Preview
@Composable
fun SpectatorScreenPreview() {
    SpectatorScreen(
        state = SpectatorContract.UiState(
            data = null
        ),
        effectFlow = null,
        onEvent = {},
        onNavigationRequested = {},
    )
}