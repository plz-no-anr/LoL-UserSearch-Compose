package com.plznoanr.lol.feature.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.collectInLaunchedEffectWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SettingRoute(
    onShowSnackbar: suspend (String) -> Boolean,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val eventChannel = remember { Channel<Event>(Channel.UNLIMITED) }
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
    SettingScreen(
        state = state,
        sideEffectFlow = viewModel.sideEffectFlow,
        onThemeChange = { onEvent(Event.OnThemeChange(it)) },
        onKeyChange = { onEvent(Event.OnKeyChange(it)) },
        onShowSnackbar = onShowSnackbar
    )

}

@Composable
internal fun SettingScreen(
    state: UiState = UiState(),
    sideEffectFlow: Flow<SideEffect>,
    onThemeChange: (Boolean) -> Unit,
    onKeyChange: (String) -> Unit,
    onShowSnackbar: suspend (String) -> Boolean,
) {
    val coroutineScope = rememberCoroutineScope()
    val (keyQuery, onQueryChange) = remember {
        mutableStateOf("")
    }
    var isShowKeyChangeDialog by remember {
        mutableStateOf(false)
    }

    sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is OnShowSnackbar -> coroutineScope.launch {
                onShowSnackbar(sideEffect.message)
            }
        }
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