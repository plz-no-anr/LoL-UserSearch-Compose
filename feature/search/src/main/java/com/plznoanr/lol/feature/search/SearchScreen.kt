package com.plznoanr.lol.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.common.model.parseError
import com.plznoanr.lol.core.designsystem.component.AppProgressBar
import com.plznoanr.lol.core.designsystem.component.error.ErrorScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun SearchRoute(
    onShowSnackbar: suspend (String) -> Boolean,
    navigateToSummoner: (String, String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    SearchScreen(
        state = uiState,
        onEvent = { coroutineScope.launch { viewModel.onEvent(it) } },
        sideEffectFlow = viewModel.sideEffectFlow,
        navigateToSummoner = navigateToSummoner,
        onShowSnackbar = onShowSnackbar
    )

}

@Composable
internal fun SearchScreen(
    state: UiState,
    onEvent: (Event) -> Unit,
    sideEffectFlow: Flow<SideEffect>,
    navigateToSummoner: (String, String) -> Unit,
    onShowSnackbar: suspend (String) -> Boolean,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        sideEffectFlow.onEach { sideEffect ->
            when (sideEffect) {
                is NavigateToSummoner -> navigateToSummoner(sideEffect.name, sideEffect.tag)
                is ShowSnackbar -> onShowSnackbar(sideEffect.message)
            }
        }.collect()
    }
    when {
        state.isLoading -> AppProgressBar()
        state.error != null -> ErrorScreen(
            error = state.error.parseError()
        ) {}

        else -> {
            SearchContent(
                data = state.data,
                name = state.query,
                isActive = state.isActive,
                onNameChange = { onEvent(OnQueryChange(it)) },
                onSearch = {
                    onEvent(OnSearch(it))
                    keyboardController?.hide()
                },
                onActiveChange = { onEvent(OnActiveChange(it)) },
                onDelete = { onEvent(OnDelete(it)) },
                onDeleteAll = { onEvent(OnDeleteAll) }
            )
        }
    }

}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        state = UiState(),
        sideEffectFlow = flow { },
        onEvent = {},
        navigateToSummoner = { n,t -> },
        onShowSnackbar = { true }
    )
}