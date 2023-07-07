package com.plz.no.anr.lol.ui.feature.summoner.composables

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
import com.plz.no.anr.lol.ui.feature.summoner.SummonerContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummonerScreen(
    state: SummonerContract.State,
    sideEffectFlow: Flow<SummonerContract.SideEffect>?,
    onIntent: (SummonerContract.Intent) -> Unit,
    onNavigationRequested: (SummonerContract.SideEffect.Navigation) -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        onIntent(SummonerContract.Intent.OnLoad)
        sideEffectFlow?.onEach { sideEffect ->
            when (sideEffect) {
                is SummonerContract.SideEffect.Toast -> snackbarHostState.showSnackbar(
                    message = sideEffect.msg,
                    duration = SnackbarDuration.Short
                )
                is SummonerContract.SideEffect.Navigation.Back -> onNavigationRequested(sideEffect)
                is SummonerContract.SideEffect.Navigation.ToSpectator -> onNavigationRequested(sideEffect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.summoner_title),
                isBackPressVisible = true,
                onBackPressed = { onIntent(SummonerContract.Intent.Navigation.Back) }
            )
        }) {
        when {
            state.isLoading -> AppProgressBar()
            state.error != null -> ErrorScreen(error = state.error.parseError()) { onIntent(SummonerContract.Intent.Navigation.Back) }
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
        state = SummonerContract.State.initial(),
        sideEffectFlow = null,
        onIntent = {},
        onNavigationRequested = {}
    )
}