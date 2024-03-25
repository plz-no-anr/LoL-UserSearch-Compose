package com.plznoanr.lol.feature.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.LocalSnackbarHostState
import com.plznoanr.lol.core.designsystem.component.collectInLaunchedEffectWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
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
            is OnShowSnackbar -> coroutineScope.launch {
                snackbarHostState.showSnackbar(sideEffect.message)
            }
        }
    }

    SettingScreen(
        state = state,
        onThemeChange = { onEvent(Event.OnThemeChange(it)) },
        onKeyChange = { onEvent(Event.OnKeyChange(it)) },
    )

}

@Composable
internal fun SettingScreen(
    state: UiState,
    onThemeChange: (Boolean) -> Unit,
    onKeyChange: (String) -> Unit,
) {
    val (keyQuery, onQueryChange) = remember {
        mutableStateOf("")
    }
    var isShowKeyChangeDialog by remember {
        mutableStateOf(false)
    }

    SettingContent(
        nickname = state.profile?.nickname,
        apiKey = state.apiKey,
        keyQuery = keyQuery,
        isDarkTheme = state.isDarkTheme,
        isShowDialog = isShowKeyChangeDialog,
        onShowDialog = { isShowKeyChangeDialog = it },
        onThemeChange = onThemeChange,
        onQueryChange = onQueryChange,
        onKeyChange = onKeyChange
    )

}


@Preview
@Composable
private fun SettingScreenPreview() {
}