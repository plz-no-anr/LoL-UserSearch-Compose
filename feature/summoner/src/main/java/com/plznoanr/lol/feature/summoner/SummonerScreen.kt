package com.plznoanr.lol.feature.summoner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.AppProgressBar
import com.plznoanr.lol.core.designsystem.component.LocalSnackbarHostState
import com.plznoanr.lol.core.designsystem.component.collectInLaunchedEffectWithLifecycle
import com.plznoanr.lol.core.designsystem.component.error.ErrorScreen
import com.plznoanr.lol.core.designsystem.component.summoner.SummonerItem
import com.plznoanr.lol.core.model.toText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

@Composable
fun SummonerRoute(
    navigateToSpectator: (String) -> Unit,
    onBackPress: () -> Unit,
    viewModel: SummonerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val eventChannel = remember { Channel<Event>(Channel.UNLIMITED) }
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
            is ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.message)
            is OnPopBack -> onBackPress()
            is NavigateToSpectator -> navigateToSpectator(sideEffect.summonerId)
        }
    }

    SummonerScreen(
        state = uiState,
        onEvent = onEvent,
    )

}

@Composable
internal fun SummonerScreen(
    state: UiState,
    onEvent: (Event) -> Unit,
) {
    when {
        state.isLoading -> AppProgressBar()
        state.error != null -> ErrorScreen(
            error = state.error
        ) { onEvent(Event.OnBackClick) }

        else -> {
            state.summoner?.also { data ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    SummonerItem(
                        icon = data.icon,
                        nickname = data.nickname.toText(),
                        level = data.levelInfo,
                        tierRank = data.tierRank,
                        tierIcon = data.tier,
                        isBookmark = data.isBookMarked,
                        isHideBookmark = true,
                        lpWinLose = data.lpWinLose,
                        progress = data.miniSeries?.progress,
                        onBookmarked = { onEvent(Event.OnBookmark(data.id)) }
                    )

                }
            }
        }
    }

}

@Preview
@Composable
private fun SummonerScreenPreview() {
    SummonerScreen(
        state = UiState(),
        onEvent = {},
    )
}