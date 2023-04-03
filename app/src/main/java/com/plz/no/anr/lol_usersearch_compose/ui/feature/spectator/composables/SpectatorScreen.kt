package com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.plz.no.anr.lol_usersearch_compose.ui.base.SIDE_EFFECTS_KEY
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.AppProgressBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.TopAppBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.error.ErrorScreen
import com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator.SpectatorContract
import com.plznoanr.data.model.common.parseError
import com.plznoanr.lol_usersearch_compose.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpectatorScreen(
    state: SpectatorContract.UiState,
    effectFlow: Flow<SpectatorContract.Effect>?,
    onEvent: (SpectatorContract.Event) -> Unit,
    onNavigationRequested: (SpectatorContract.Effect.Navigation) -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        onEvent(SpectatorContract.Event.OnLoad)
        effectFlow?.onEach { effect ->
            when (effect) {
                is SpectatorContract.Effect.Toast -> snackbarHostState.showSnackbar(
                    message = effect.msg,
                    duration = SnackbarDuration.Short
                )
                is SpectatorContract.Effect.Navigation.Back -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.spectator_title),
                isBackPressVisible = true,
                onBackPressed = { onEvent(SpectatorContract.Event.Navigation.Back) }
            )
        }) {
        when {
            state.isLoading -> AppProgressBar()
            state.error != null -> ErrorScreen(error = state.error.parseError())
            else -> {
                state.data?.let { data ->
                    SpectatorView(
                        modifier = Modifier
                            .padding(it),
                        data = data
                    )
                }

            }
        }
    }
}


@Preview
@Composable
private fun SpectatorScreenPreview() {
    SpectatorScreen(
        state = SpectatorContract.UiState.initial(),
        effectFlow = null,
        onEvent = {},
        onNavigationRequested = {},
    )
}