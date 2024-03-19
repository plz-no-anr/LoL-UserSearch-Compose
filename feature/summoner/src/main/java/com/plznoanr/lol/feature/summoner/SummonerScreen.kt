package com.plznoanr.lol.feature.summoner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.common.model.parseError
import com.plznoanr.lol.core.designsystem.component.AppProgressBar
import com.plznoanr.lol.core.designsystem.component.error.ErrorScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

@Composable
fun SummonerRoute(
    onShowSnackbar: suspend (String) -> Boolean,
    navigateToSpectator: (String) -> Unit,
    onBackPress: () -> Unit,
    viewModel: SummonerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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

    SummonerScreen(
        state = uiState,
        onEvent = onEvent,
        sideEffectFlow = viewModel.sideEffectFlow,
        onNavigateToSpectator = navigateToSpectator,
        onBackPress = onBackPress,
        onShowSnackbar = onShowSnackbar
    )

}

@Composable
internal fun SummonerScreen(
    state: UiState,
    onEvent: (Event) -> Unit,
    sideEffectFlow: Flow<SideEffect>,
    onNavigateToSpectator: (String) -> Unit,
    onBackPress: () -> Unit,
    onShowSnackbar: suspend (String) -> Boolean
) {
    LaunchedEffect(Unit) {
        sideEffectFlow.onEach { sideEffect ->
            when (sideEffect) {
                is ShowSnackbar -> onShowSnackbar(sideEffect.message)
                is OnPopBack -> onBackPress()
                is NavigateToSpectator -> onNavigateToSpectator(sideEffect.summonerId)
            }
        }.collect()
    }

    when {
        state.isLoading -> AppProgressBar()
        state.errorMsg != null -> ErrorScreen(
            error = state.errorMsg.parseError()
        ) { onEvent(Event.OnBackClick) }
        else -> {
            state.summoner?.also { data ->
                SummonerContent(
                    modifier = Modifier,
                    summoner = data,
                    onWatch = { onEvent(Event.OnWatch(it)) },
                    onBookmark = { onEvent(Event.OnBookmark(it)) }
                )
            }
        }
    }

}

@Preview
@Composable
private fun SummonerScreenPreview() {
    SummonerScreen(
        state = UiState(),
        onNavigateToSpectator = {},
        onShowSnackbar = {true},
        onBackPress = {},
        onEvent = {},
        sideEffectFlow = flow {  }
    )
}