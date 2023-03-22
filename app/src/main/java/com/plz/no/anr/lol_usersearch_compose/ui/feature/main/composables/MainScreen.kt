package com.plz.no.anr.lol_usersearch_compose.ui.feature.main.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.DrawerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
            Drawers(data = profileState)
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

            }
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    summonerList: List<Summoner>,
    pullRefreshState: PullRefreshState,
    onEvent: (MainContract.Event) -> Unit
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
                .clickable { onEvent(MainContract.Event.OnDeleteAll) },
        )
        Box(
            modifier = Modifier
                .pullRefresh(state = pullRefreshState)
        ) {
            LazyColumn {
                items(summonerList) {
                    MainItem(summoner = it) { event ->
                        onEvent(event)
                    }
                }
            }
        }

    }
}

@Composable
fun Drawers(
    data: Profile,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data.icon)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .padding(top = 30.dp, start = 16.dp, bottom = 16.dp)
            .size(size = 100.dp)
            .clip(RoundedCornerShape(10)),
        contentScale = ContentScale.FillWidth,
    )

    Text(
        text = data.name,
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 16.dp)
    )

    Text(
        text = data.getLevels(),
        color = Color.White,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 16.dp)
    )

    Divider(
        color = Color.White,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    )
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
