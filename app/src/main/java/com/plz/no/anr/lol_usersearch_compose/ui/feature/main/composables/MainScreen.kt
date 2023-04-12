package com.plz.no.anr.lol_usersearch_compose.ui.feature.main.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plz.no.anr.lol_usersearch_compose.ui.base.SIDE_EFFECTS_KEY
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.AppProgressBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.GetApiKeyView
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.TopAppBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.error.ErrorScreen
import com.plz.no.anr.lol_usersearch_compose.ui.feature.main.MainContract
import com.plz.no.anr.lol_usersearch_compose.ui.theme.sky
import com.plznoanr.data.model.common.parseError
import com.plznoanr.domain.model.Profile
import com.plznoanr.lol_usersearch_compose.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    state: MainContract.UiState,
    effectFlow: Flow<MainContract.Effect>?,
    onEvent: (MainContract.Event) -> Unit,
    onNavigationRequested: (MainContract.Effect.Navigation) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    var profileState by remember {
        mutableStateOf(getDummyProfile())
    }
    var keyState by remember {
        mutableStateOf<String?>(null)
    }

    val (getKeyVisible, setKeyVisible) = remember { mutableStateOf(false) }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        onEvent(MainContract.Event.OnLoad)
        effectFlow?.onEach { effect ->
            when (effect) {
                is MainContract.Effect.Navigation.ToSearch -> onNavigationRequested(effect)
                is MainContract.Effect.Navigation.ToSpectator -> onNavigationRequested(effect)
                is MainContract.Effect.Toast -> scaffoldState.snackbarHostState.showSnackbar(
                    message = effect.message,
                    duration = SnackbarDuration.Short
                )
                is MainContract.Effect.MoveGetApiKey -> setKeyVisible(true)
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
                onGetKey = {
                    onEvent(MainContract.Event.Key.OnGet)
                },
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
            state.error != null -> ErrorScreen(error = state.error.parseError()) { onEvent(MainContract.Event.OnLoad) }
            else -> {
                MainContent(
                    modifier = Modifier.padding(it),
                    data = state.data,
                    isRefreshing = state.isRefreshing,
                ) { event ->
                    onEvent(event)
                }
                state.profile?.also { profile ->
                    profileState = profile
                }
                keyState = state.key
            }
        }
        if (getKeyVisible) GetApiKeyView()
    }
}


@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen(
        state = MainContract.UiState.initial(),
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
    icon = "http://ddragon.leagueoflegends.com/cdn/13.6.1/img/profileicon/6.png",
    level = "100"
)
