package com.plz.no.anr.lol.ui.feature.spectator.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.plz.no.anr.lol.R
import com.plz.no.anr.lol.data.model.common.parseError
import com.plz.no.anr.lol.ui.base.SIDE_EFFECTS_KEY
import com.plz.no.anr.lol.ui.feature.common.AppProgressBar
import com.plz.no.anr.lol.ui.feature.common.TopAppBar
import com.plz.no.anr.lol.ui.feature.common.error.ErrorScreen
import com.plz.no.anr.lol.ui.feature.spectator.SpectatorContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpectatorScreen(
    state: SpectatorContract.State,
    sideEffectFlow: Flow<SpectatorContract.SideEffect>?,
    onIntent: (SpectatorContract.Intent) -> Unit,
    onNavigationRequested: (SpectatorContract.SideEffect.Navigation) -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        onIntent(SpectatorContract.Intent.OnLoad)
        sideEffectFlow?.onEach { sideEffect ->
            when (sideEffect) {
                is SpectatorContract.SideEffect.Toast -> snackbarHostState.showSnackbar(
                    message = sideEffect.msg,
                    duration = SnackbarDuration.Short
                )
                is SpectatorContract.SideEffect.Navigation.Back -> onNavigationRequested(sideEffect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.spectator_title),
                isBackPressVisible = true,
                onBackPressed = { onIntent(SpectatorContract.Intent.Navigation.Back) }
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
        state = SpectatorContract.State.initial(),
        sideEffectFlow = null,
        onIntent = {},
        onNavigationRequested = {},
    )
}