package com.plznoanr.lol.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.AppProgressBar
import com.plznoanr.lol.core.designsystem.component.LocalSnackbarHostState
import com.plznoanr.lol.core.designsystem.component.collectInLaunchedEffectWithLifecycle
import com.plznoanr.lol.core.designsystem.component.error.ErrorScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SearchRoute(
    navigateToSummoner: (String, String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val eventChannel = remember { Channel<Event>(Channel.UNLIMITED) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState by rememberUpdatedState(LocalSnackbarHostState.current)

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main.immediate) {
            eventChannel
                .consumeAsFlow()
                .onEach(viewModel::onEvent)
                .collect()
        }
    }
    val onEvent = remember {
        { event: Event ->
            eventChannel.trySend(event).getOrThrow()
        }
    }

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is NavigateToSummoner -> navigateToSummoner(sideEffect.name, sideEffect.tag)
            is ShowSnackbar -> coroutineScope.launch {
                snackbarHostState.showSnackbar(sideEffect.message)
            }
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