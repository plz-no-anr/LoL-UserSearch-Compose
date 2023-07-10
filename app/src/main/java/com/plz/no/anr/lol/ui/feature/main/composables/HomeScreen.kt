package com.plz.no.anr.lol.ui.feature.main.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plz.no.anr.lol.R
import com.plz.no.anr.lol.data.model.common.parseError
import com.plz.no.anr.lol.domain.model.Profile
import com.plz.no.anr.lol.ui.base.SIDE_EFFECTS_KEY
import com.plz.no.anr.lol.ui.feature.common.AppProgressBar
import com.plz.no.anr.lol.ui.feature.common.GetApiKeyView
import com.plz.no.anr.lol.ui.feature.common.TopAppBar
import com.plz.no.anr.lol.ui.feature.common.error.ErrorScreen
import com.plz.no.anr.lol.ui.feature.main.HomeContract
import com.plz.no.anr.lol.ui.theme.sky
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    state: HomeContract.State,
    sideEffectFlow: Flow<HomeContract.SideEffect>?,
    onIntent: (HomeContract.Intent) -> Unit,
    onNavigationRequested: (HomeContract.SideEffect.Navigation) -> Unit,
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
        onIntent(HomeContract.Intent.OnLoad)
        sideEffectFlow?.onEach { sideEffect ->
            when (sideEffect) {
                is HomeContract.SideEffect.Navigation.ToSearch -> onNavigationRequested(sideEffect)
                is HomeContract.SideEffect.Navigation.ToSpectator -> onNavigationRequested(sideEffect)
                is HomeContract.SideEffect.Toast -> scaffoldState.snackbarHostState.showSnackbar(
                    message = sideEffect.message,
                    duration = SnackbarDuration.Short
                )
                is HomeContract.SideEffect.MoveGetApiKey -> setKeyVisible(true)
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
                IconButton(onClick = { onIntent(HomeContract.Intent.OnSearch) }) {
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
                    onIntent(HomeContract.Intent.Key.OnGet)
                },
                onAddKey = {
                    it.run {
                        if (isNotEmpty()) {
                            onIntent(HomeContract.Intent.Key.OnAdd(it))
                        }
                    }
                },
                onDeleteKey = {
                    onIntent(HomeContract.Intent.Key.OnDelete)
                }
            )
        },
        drawerBackgroundColor = sky,
        drawerContentColor = Color.White,
        drawerShape = RoundedCornerShape(10.dp),
    ) {
        when {
            state.isLoading -> AppProgressBar()
            state.error != null -> ErrorScreen(error = state.error.parseError()) { onIntent(HomeContract.Intent.OnLoad) }
            else -> {
                HomeContent(
                    modifier = Modifier.padding(it),
                    data = state.data,
                    isRefreshing = state.isRefreshing,
                ) { intent ->
                    onIntent(intent)
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
private fun HomeScreenPreview() {
    HomeScreen(
        state = HomeContract.State.initial(),
        sideEffectFlow = null,
        onIntent = {},
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
