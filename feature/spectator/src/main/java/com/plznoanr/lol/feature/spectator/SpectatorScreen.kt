package com.plznoanr.lol.feature.spectator

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

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