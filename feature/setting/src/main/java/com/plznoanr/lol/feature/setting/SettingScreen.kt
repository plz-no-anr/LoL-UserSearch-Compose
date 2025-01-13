package com.plznoanr.lol.feature.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.LocalSnackbarHostState
import com.plznoanr.lol.core.designsystem.component.collectInLaunchedEffectWithLifecycle
import com.plznoanr.lol.core.mvibase.rememberEvent

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState by rememberUpdatedState(LocalSnackbarHostState.current)
    val onEvent = rememberEvent(viewModel::onEvent)

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is OnShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.message)
        }
    }

    SettingScreen(
        state = state,
        onThemeChange = { onEvent(Event.OnThemeChange(it)) },
        onKeyChange = { onEvent(Event.OnKeyChange(it)) },
        onQueryChange = { onEvent(Event.OnQueryChange(it)) },
        onShowDialog = { onEvent(Event.OnShowDialog(it)) }
    )

}

@Composable
internal fun SettingScreen(
    state: UiState,
    onThemeChange: (Boolean) -> Unit,
    onKeyChange: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onShowDialog: (Boolean) -> Unit
) {
    SettingContent(
        nickname = state.profile?.nickname,
        apiKey = state.apiKey,
        keyQuery = state.keyQuery,
        isDarkTheme = state.isDarkTheme,
        isShowDialog = state.showKeyChangeDialog,
        onShowDialog = onShowDialog,
        onThemeChange = onThemeChange,
        onQueryChange = onQueryChange,
        onKeyChange = onKeyChange
    )
}


@Preview
@Composable
private fun SettingScreenPreview() {
}