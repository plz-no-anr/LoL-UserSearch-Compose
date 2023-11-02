package com.plznoanr.lol.ui.feature.summoner.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.plznoanr.lol.R
import com.plznoanr.lol.core.common.model.parseError
import com.plznoanr.lol.ui.feature.common.AppProgressBar
import com.plznoanr.lol.ui.feature.common.TopAppBar
import com.plznoanr.lol.ui.feature.common.error.ErrorScreen
import com.plznoanr.lol.ui.feature.summoner.SummonerIntent
import com.plznoanr.lol.ui.feature.summoner.SummonerSideEffect
import com.plznoanr.lol.ui.feature.summoner.SummonerUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummonerScreen(
    state: SummonerUiState,
    sideEffectFlow: Flow<SummonerSideEffect>?,
    onIntent: (SummonerIntent) -> Unit,
    onNavigationRequested: (SummonerSideEffect.Navigation) -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        onIntent(SummonerIntent.OnLoad)
        sideEffectFlow?.onEach { sideEffect ->
            when (sideEffect) {
                is SummonerSideEffect.Toast -> snackbarHostState.showSnackbar(
                    message = sideEffect.msg,
                    duration = SnackbarDuration.Short
                )
                is SummonerSideEffect.Navigation.Back -> onNavigationRequested(sideEffect)
                is SummonerSideEffect.Navigation.ToSpectator -> onNavigationRequested(sideEffect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.summoner_title),
                isBackPressVisible = true,
                onBackPressed = { onIntent(SummonerIntent.Navigation.Back) }
            )
        }) {
        when {
            state.isLoading -> AppProgressBar()
            state.error != null -> ErrorScreen(error = state.error.parseError()) { onIntent(SummonerIntent.Navigation.Back) }
            else -> {
                state.data?.also { data ->
                    SummonerContent(
                        modifier = Modifier.padding(it),
                        data = data,
                        onIntent = onIntent
                    )
                }
            }
        }

    }

}

@Preview
@Composable
private fun SummonerScreenPreview() {
    SummonerScreen(
        state = SummonerUiState(),
        sideEffectFlow = null,
        onIntent = {},
        onNavigationRequested = {}
    )
}