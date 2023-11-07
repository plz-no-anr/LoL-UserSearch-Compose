package com.plznoanr.lol.feature.summoner

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.common.model.parseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SummonerRoute(
    viewModel: SummonerViewModel = hiltViewModel()
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    SummonerScreen(
        state = uiState,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::postIntent,
        onNavigationRequested = {}
    )

}

@Composable
internal fun SummonerScreen(
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

    when {
        state.isLoading -> com.plznoanr.lol.core.designsystem.component.AppProgressBar()
        state.error != null -> com.plznoanr.lol.core.designsystem.component.error.ErrorScreen(
            error = state.error.parseError()
        ) { onIntent(SummonerIntent.Navigation.Back) }
        else -> {
            state.data?.also { data ->
                SummonerContent(
                    modifier = Modifier,
                    data = data,
                    onIntent = onIntent
                )
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