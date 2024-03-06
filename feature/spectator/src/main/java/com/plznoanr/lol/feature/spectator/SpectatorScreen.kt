package com.plznoanr.lol.feature.spectator

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.plznoanr.lol.core.common.model.parseError
import com.plznoanr.lol.core.designsystem.component.DefaultTopAppBar
import com.plznoanr.lol.core.mvibase.MviViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SpectatorRoute(
    viewModel: SpectatorViewModel = hiltViewModel()
) {



}

@Composable
internal fun SpectatorScreen(
    state: SpectatorUiState,
//    sideEffectFlow: Flow<SpectatorSideEffect>?,
//    onIntent: (SpectatorIntent) -> Unit,
//    onNavigationRequested: (SpectatorSideEffect.Navigation) -> Unit,
) {

//    LaunchedEffect(Unit) {
//        onIntent(SpectatorIntent.OnLoad)
//        sideEffectFlow?.onEach { sideEffect ->
//            when (sideEffect) {
//                is SpectatorSideEffect.Toast -> snackbarHostState.showSnackbar(
//                    message = sideEffect.msg,
//                    duration = SnackbarDuration.Short
//                )
//                is SpectatorSideEffect.Navigation.Back -> onNavigationRequested(sideEffect)
//            }
//        }?.collect()
//    }

//    Scaffold(
//        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
//        topBar = {
//            DefaultTopAppBar(
//                titleRes = R.string.spectator_title
//            )
//        }) {
//        when {
//            state.isLoading -> com.plznoanr.lol.core.designsystem.component.AppProgressBar()
//            state.error != null -> com.plznoanr.lol.core.designsystem.component.error.ErrorScreen(
//                error = state.error.parseError()
//            )
//            else -> {
//                state.data?.let { data ->
//                    SpectatorContent(
//                        modifier = Modifier
//                            .padding(it),
//                        data = data
//                    )
//                }
//
//            }
//        }
//    }
}


@Preview
@Composable
private fun SpectatorScreenPreview() {
    SpectatorScreen(
        state = SpectatorUiState(),
//        sideEffectFlow = null,
//        onIntent = {},
//        onNavigationRequested = {},
    )
}