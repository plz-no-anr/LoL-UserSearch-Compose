package com.plznoanr.lol.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.AppProgressBar
import com.plznoanr.lol.core.designsystem.component.LocalSnackbarHostState
import com.plznoanr.lol.core.designsystem.component.collectInLaunchedEffectWithLifecycle
import com.plznoanr.lol.core.mvibase.rememberEvent
import com.plznoanr.lol.core.ui.error.ErrorScreen

@Composable
fun SearchRoute(
    navigateToSummoner: (String, String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState by rememberUpdatedState(LocalSnackbarHostState.current)
    val onEvent = rememberEvent(viewModel::onEvent)

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is NavigateToSummoner -> navigateToSummoner(sideEffect.name, sideEffect.tag)
            is ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.message)
        }
    }

    SearchScreen(
        state = uiState,
        onEvent = onEvent,
    )

}

@Composable
internal fun SearchScreen(
    state: UiState,
    onEvent: (Event) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    when {
        state.isLoading -> AppProgressBar()
        state.error != null -> ErrorScreen(
            error = state.error
        ) {}

        else -> {
            SearchContent(
                data = state.data,
                name = state.query,
                isActive = state.isActive,
                onNameChange = { onEvent(Event.OnQueryChange(it)) },
                onSearch = {
                    onEvent(Event.OnSearch(it))
                    keyboardController?.hide()
                },
                onActiveChange = { onEvent(Event.OnActiveChange(it)) },
                onDelete = { onEvent(Event.OnDelete(it)) },
                onDeleteAll = { onEvent(Event.OnDeleteAll) }
            )
        }
    }

}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        state = UiState(),
        onEvent = {},
    )
}