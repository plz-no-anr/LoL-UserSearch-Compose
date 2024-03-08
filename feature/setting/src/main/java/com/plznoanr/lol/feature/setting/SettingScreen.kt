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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun SettingRoute(
    onShowSnackbar: suspend (String) -> Boolean,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    SettingScreen(
        state = state,
        sideEffectFlow = viewModel.sideEffectFlow,
        onThemeChange = { coroutineScope.launch { viewModel.onEvent(OnThemeChange(it)) } },
        onKeyChange = { coroutineScope.launch { viewModel.onEvent(OnKeyChange(it)) } },
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

    LaunchedEffect(Unit) {
        sideEffectFlow.onEach { sideEffect ->
            when (sideEffect) {
                is OnShowSnackbar -> coroutineScope.launch {
                    onShowSnackbar(sideEffect.message)
                }
            }
        }.collect()
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