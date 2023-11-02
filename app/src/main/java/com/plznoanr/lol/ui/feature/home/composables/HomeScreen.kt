package com.plznoanr.lol.ui.feature.home.composables

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plznoanr.lol.R
import com.plznoanr.lol.core.common.model.parseError
import com.plznoanr.lol.ui.feature.common.AppProgressBar
import com.plznoanr.lol.ui.feature.common.GetApiKeyView
import com.plznoanr.lol.ui.feature.common.TopAppBar
import com.plznoanr.lol.ui.feature.common.error.ErrorScreen
import com.plznoanr.lol.ui.feature.home.HomeIntent
import com.plznoanr.lol.ui.feature.home.HomeSideEffect
import com.plznoanr.lol.ui.feature.home.HomeUiState
import com.plznoanr.lol.ui.theme.sky
import com.plznoanr.lol.core.model.Profile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    state: HomeUiState,
    sideEffectFlow: Flow<HomeSideEffect>?,
    onIntent: (HomeIntent) -> Unit,
    onNavigationRequested: (HomeSideEffect.Navigation) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val (getKeyVisible, setKeyVisible) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onIntent(HomeIntent.OnLoad)
        sideEffectFlow?.onEach { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.Navigation.ToSearch -> onNavigationRequested(sideEffect)
                is HomeSideEffect.Navigation.ToSpectator -> onNavigationRequested(sideEffect)
                is HomeSideEffect.Toast -> scaffoldState.snackbarHostState.showSnackbar(
                    message = sideEffect.message,
                    duration = SnackbarDuration.Short
                )
                is HomeSideEffect.MoveGetApiKey -> setKeyVisible(true)
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
                IconButton(onClick = { onIntent(HomeIntent.OnSearch) }) {
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
                data = state.profile ?: getDummyProfile(),
                apiKey = state.key,
                onGetKey = {
                    onIntent(HomeIntent.Key.OnGet)
                },
                onAddKey = {
                    it.run {
                        if (isNotEmpty()) {
                            onIntent(HomeIntent.Key.OnAdd(it))
                        }
                    }
                },
                onDeleteKey = {
                    onIntent(HomeIntent.Key.OnDelete)
                }
            )
        },
        drawerBackgroundColor = sky,
        drawerContentColor = Color.White,
        drawerShape = RoundedCornerShape(10.dp),
    ) {
        when {
            state.isLoading -> AppProgressBar()
            state.error != null -> ErrorScreen(error = state.error.parseError()) { onIntent(HomeIntent.OnLoad) }
            else -> {
                HomeContent(
                    modifier = Modifier.padding(it),
                    data = state.data,
                    isRefreshing = state.isRefreshing,
                ) { intent ->
                    onIntent(intent)
                }
            }
        }
        if (getKeyVisible) GetApiKeyView()
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        state = HomeUiState(),
        sideEffectFlow = null,
        onIntent = {},
        onNavigationRequested = {}
    )
}

private suspend fun onDrawerEvent(drawerState: DrawerState) = drawerState.also {
    if (it.isOpen) it.close() else it.open()
}

private fun getDummyProfile() = Profile(
    id = "id",
    name = "name",
    icon = "http://ddragon.leagueoflegends.com/cdn/13.6.1/img/profileicon/6.png",
    level = "100"
)
