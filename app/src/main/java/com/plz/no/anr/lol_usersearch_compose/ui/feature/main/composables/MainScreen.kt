package com.plz.no.anr.lol_usersearch_compose.ui.feature.main.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plz.no.anr.lol_usersearch_compose.ui.base.SIDE_EFFECTS_KEY
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.AppProgressBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.ErrorScreen
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.TopAppBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.main.MainContract
import com.plz.no.anr.lol_usersearch_compose.ui.theme.sky
import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.model.Summoner
import com.plznoanr.lol_usersearch_compose.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    state: MainContract.UiState,
    effectFlow: Flow<MainContract.Effect>?,
    onEvent: (MainContract.Event) -> Unit,
    onNavigationRequested: (MainContract.Effect.Navigation) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember {
        mutableStateOf(false)
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                onEvent(MainContract.Event.Refresh)
            }
        }
    )
    val scaffoldState = rememberScaffoldState()
    var profileState by remember {
        mutableStateOf(getDummyProfile())
    }
    var keyState by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        onEvent(MainContract.Event.OnLoad)
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
                isDrawerVisible = true,
                onDrawerClick = {
                    coroutineScope.launch {
                        delay(200)
                        onDrawerEvent(scaffoldState.drawerState)
                    }
                },
            ) {
                IconButton(onClick = { onEvent(MainContract.Event.OnSearch) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawers(
                data = profileState,
                apiKey = keyState,
                onAddKey = {
                    it.run {
                        if (isNotEmpty()) {
                            onEvent(MainContract.Event.Key.OnAdd(it))
                        }
                    }
                },
                onDeleteKey = {
                    onEvent(MainContract.Event.Key.OnDelete)
                }
            )
        },
        drawerBackgroundColor = sky,
        drawerContentColor = Color.White,
        drawerShape = RoundedCornerShape(10.dp),
    ) {
        when {
            state.isLoading -> AppProgressBar()
            state.error != null -> ErrorScreen(errMsg = state.error) {
                onEvent(MainContract.Event.OnLoad)
            }
            else -> {
                MainView(
                    modifier = Modifier.padding(it),
                    summonerList = state.data,
                    pullRefreshState = pullRefreshState
                ) { event ->
                    onEvent(event)
                }
                state.profile?.let { profile ->
                    profileState = profile
                }
                keyState = state.key

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
            data = (0..10).map {
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
            },
            error = null
        ),
        effectFlow = null,
        onEvent = {},
        onNavigationRequested = {}
    )
}

private suspend fun onDrawerEvent(drawerState: DrawerState) = drawerState.also {
    if (it.isOpen) it.close() else it.open()
}

private fun getDummyProfile() = Profile(
    name = "name",
    icon = "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/6.png",
    level = "100"
)
