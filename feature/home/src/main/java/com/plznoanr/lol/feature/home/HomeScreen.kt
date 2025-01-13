package com.plznoanr.lol.feature.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.AppProgressBar
import com.plznoanr.lol.core.designsystem.component.OnBottomReached
import com.plznoanr.lol.core.designsystem.component.collectInLaunchedEffectWithLifecycle
import com.plznoanr.lol.core.model.Profile
import com.plznoanr.lol.core.mvibase.rememberEvent
import com.plznoanr.lol.core.ui.error.ErrorScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navCallbackFlow: () -> Flow<Boolean>
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent = rememberEvent(viewModel::onEvent)

    val lazyListState = rememberLazyListState().apply {
        OnBottomReached {
            if (state.summonerList.size >= 20) {
                onEvent(Event.OnNextPage)
            }
        }
    }

    LaunchedEffect(Unit) {
        navCallbackFlow()
            .onEach {
                lazyListState.animateScrollToItem(0)
            }.collect()
    }

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            else -> Unit
        }
    }


    HomeScreen(
        state = state,
        onEvent = onEvent,
        lazyListState = lazyListState
    )
}

@Composable
internal fun HomeScreen(
    state: UiState,
    onEvent: (Event) -> Unit,
    lazyListState: LazyListState
) {
    when {
        state.isLoading -> AppProgressBar()
        state.error != null -> ErrorScreen(
            error = state.error
        ) { onEvent(Event.OnInit) }

        else -> {
            HomeContent(
                data = state.summonerList,
                isRefreshing = state.isRefreshing,
                isLoadNextPage = state.isLoadNextPage,
                isSortedBookmark = state.isSortedBookmark,
                lazyListState = lazyListState,
                onRefresh = { onEvent(Event.OnRefresh) },
                onBookmarked = { onEvent(Event.OnBookmark(it)) },
                onDeleteAll = { onEvent(Event.OnDeleteAll) },
                onSortedBookmark = { onEvent(Event.OnSortedBookmark(it)) }
            )
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
//    HomeScreen(
//        state = UiState(),
//        onEvent = {},
//    )
}

private fun getDummyProfile() = Profile(
    id = "id",
    nickname = "name",
    icon = "http://ddragon.leagueoflegends.com/cdn/13.6.1/img/profileicon/6.png",
    level = "100"
)
