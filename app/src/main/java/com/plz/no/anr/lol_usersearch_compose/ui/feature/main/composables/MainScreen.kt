package com.plz.no.anr.lol_usersearch_compose.ui.feature.main.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plz.no.anr.lol_usersearch_compose.ui.base.SIDE_EFFECTS_KEY
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.TopAppBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.main.MainContract
import com.plznoanr.domain.model.Summoner
import com.plznoanr.lol_usersearch_compose.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    state: MainContract.UiState,
    effectFlow: Flow<MainContract.Effect>?,
    onEvent: (MainContract.Event) -> Unit,
    onNavigationRequested: (MainContract.Effect.Navigation) -> Unit,
) {
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember {
        mutableStateOf(false)
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            refreshScope.launch {
                isRefreshing = true
                onEvent(MainContract.Event.Refresh)
            }
        }
    )

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is MainContract.Effect.Navigation.ToSearch -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.main_title),
                isBackPressVisible = false,
            ) {
                IconButton(onClick = { onEvent(MainContract.Event.OnSearchClick) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

            }
        }
    ) {
        when {
            state.isLoading -> {}
            state.error != null -> {}
            else -> {
                MainView(
                    modifier = Modifier.padding(it),
                    summonerList = state.data,
                    pullRefreshState = pullRefreshState
                )
            }
        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    summonerList: List<Summoner>,
    pullRefreshState: PullRefreshState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.delete_all),
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp, end = 16.dp)
        )
        Box(
            modifier = Modifier
                .pullRefresh(state = pullRefreshState)
        ) {
            LazyColumn() {
                items(summonerList) {
                    MainItem(summoner = it)
                }
            }
        }

    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        state = MainContract.UiState(
            isLoading = false,
            data = listOf(
                Summoner(
                    name = "name",
                    level = "100",
                    icon = "",
                    tier = "tier",
                    rank = "rank",
                    leaguePoints = 32,
                    wins = 20,
                    losses = 20,
                    isPlaying = false
                )
            ),
            error = null
        ),
        effectFlow = null,
        onEvent = {},
        onNavigationRequested = {}
    )
}
