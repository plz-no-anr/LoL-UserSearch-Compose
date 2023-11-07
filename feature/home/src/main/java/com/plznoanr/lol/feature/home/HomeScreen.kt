package com.plznoanr.lol.feature.home

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.common.model.parseError
import com.plznoanr.lol.core.designsystem.component.AppProgressBar
import com.plznoanr.lol.core.designsystem.component.error.ErrorScreen
import com.plznoanr.lol.core.model.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::postIntent,
        onNavigationRequested = {}
    )
}

@Composable
internal fun HomeScreen(
    state: HomeUiState,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    sideEffectFlow: Flow<HomeSideEffect>?,
    onIntent: (HomeIntent) -> Unit,
    onNavigationRequested: (HomeSideEffect.Navigation) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val (getKeyVisible, setKeyVisible) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onIntent(HomeIntent.OnLoad)
        sideEffectFlow?.onEach { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.Navigation.ToSearch -> onNavigationRequested(sideEffect)
                is HomeSideEffect.Navigation.ToSpectator -> onNavigationRequested(sideEffect)
                is HomeSideEffect.Toast -> coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = sideEffect.message,
                        duration = SnackbarDuration.Short
                    )
                }

                is HomeSideEffect.MoveGetApiKey -> setKeyVisible(true)
            }
        }?.collect()
    }


    when {
        state.isLoading -> AppProgressBar()
        state.error != null -> ErrorScreen(
            error = state.error.parseError()
        ) { onIntent(HomeIntent.OnLoad) }

        else -> {
            HomeContent(
                data = state.data,
                isRefreshing = state.isRefreshing,
            ) { intent ->
                onIntent(intent)
            }
        }
    }
    if (getKeyVisible) com.plznoanr.lol.core.designsystem.component.GetApiKeyView()
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


private fun getDummyProfile() = Profile(
    id = "id",
    name = "name",
    icon = "http://ddragon.leagueoflegends.com/cdn/13.6.1/img/profileicon/6.png",
    level = "100"
)
