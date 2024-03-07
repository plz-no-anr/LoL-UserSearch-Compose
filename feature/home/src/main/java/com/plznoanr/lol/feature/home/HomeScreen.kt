package com.plznoanr.lol.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.common.model.parseError
import com.plznoanr.lol.core.designsystem.component.AppProgressBar
import com.plznoanr.lol.core.designsystem.component.error.ErrorScreen
import com.plznoanr.lol.core.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    HomeScreen(
        state = state,
        onEvent = { coroutineScope.launch { viewModel.onEvent(it) } },
        sideEffectFlow = viewModel.sideEffectFlow
    )
}

@Composable
internal fun HomeScreen(
    state: UiState,
    onEvent: (Event) -> Unit,
    sideEffectFlow: Flow<SideEffect>,
) {
    LaunchedEffect(Unit) {
        sideEffectFlow.onEach { sideEffect ->
            when (sideEffect) {
                else -> Unit
            }
        }.collect()
    }
    Timber.d("HomeScreen: $state")
    when {
        state.isLoading -> AppProgressBar()
        state.error != null -> ErrorScreen(
            error = state.error.parseError()
        ) { onEvent(OnInit) }
        else -> {
            HomeContent(
                data = state.summonerList,
                isRefreshing = state.isRefreshing,
                onRefresh = { onEvent(OnRefresh) },
                onNextPage = { onEvent(OnNextPage) },
                onBookmarked = { onEvent(OnBookmark(it)) },
                onDeleteAll = { onEvent(OnDeleteAll) },
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
