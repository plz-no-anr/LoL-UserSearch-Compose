package com.plznoanr.lol.ui.feature.spectator.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.plznoanr.lol.R
import com.plznoanr.lol.ui.feature.common.AppProgressBar
import com.plznoanr.lol.ui.feature.common.TopAppBar
import com.plznoanr.lol.ui.feature.common.error.ErrorScreen
import com.plznoanr.lol.ui.feature.spectator.SpectatorIntent
import com.plznoanr.lol.ui.feature.spectator.SpectatorSideEffect
import com.plznoanr.lol.ui.feature.spectator.SpectatorUiState
import com.plznoanr.model.common.parseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpectatorScreen(
    state: SpectatorUiState,
    sideEffectFlow: Flow<SpectatorSideEffect>?,
    onIntent: (SpectatorIntent) -> Unit,
    onNavigationRequested: (SpectatorSideEffect.Navigation) -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        onIntent(SpectatorIntent.OnLoad)
        sideEffectFlow?.onEach { sideEffect ->
            when (sideEffect) {
                is SpectatorSideEffect.Toast -> snackbarHostState.showSnackbar(
                    message = sideEffect.msg,
                    duration = SnackbarDuration.Short
                )
                is SpectatorSideEffect.Navigation.Back -> onNavigationRequested(sideEffect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.spectator_title),
                isBackPressVisible = true,
                onBackPressed = { onIntent(SpectatorIntent.Navigation.Back) }
            )
        }) {
        when {
            state.isLoading -> AppProgressBar()
            state.error != null -> ErrorScreen(error = state.error.parseError())
            else -> {
                state.data?.let { data ->
                    SpectatorContent(
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
        state = SpectatorUiState(),
        sideEffectFlow = null,
        onIntent = {},
        onNavigationRequested = {},
    )
}