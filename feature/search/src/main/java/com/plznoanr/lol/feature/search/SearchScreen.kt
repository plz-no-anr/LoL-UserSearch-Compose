package com.plznoanr.lol.feature.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.common.model.parseError
import com.plznoanr.lol.core.designsystem.component.AppProgressBar
import com.plznoanr.lol.core.designsystem.component.DefaultTopAppBar
import com.plznoanr.lol.core.designsystem.component.error.ErrorScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
        state = uiState,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::postIntent,
        onNavigationRequested = {}
    )

}

@Composable
internal fun SearchScreen(
    state: SearchUiState,
    sideEffectFlow: Flow<SearchSideEffect>?,
    onIntent: (SearchIntent) -> Unit,
    onNavigationRequested: (SearchSideEffect.Navigation) -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        onIntent(SearchIntent.OnLoad)
        sideEffectFlow?.onEach { sideEffect ->
            when (sideEffect) {
                is SearchSideEffect.Toast -> snackbarHostState.showSnackbar(
                    message = sideEffect.msg,
                    duration = SnackbarDuration.Short
                )
                is SearchSideEffect.Navigation.Back -> onNavigationRequested(sideEffect)
                is SearchSideEffect.Navigation.ToSummoner -> onNavigationRequested(sideEffect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            DefaultTopAppBar(
                titleRes = R.string.search_title,
            )
        }
    ) {
        when {
            state.isLoading -> AppProgressBar()
            state.error != null -> ErrorScreen(
                error = state.error.parseError()
            ) {}
            else -> {
                SearchContent(
                    modifier = Modifier
                        .padding(it),
                    data = state.data,
                    name = state.name,
                    onNameChange = { summonerName -> onIntent(SearchIntent.Summoner.OnNameChanged(summonerName)) },
                    onIntent = onIntent,
                )
            }
        }

    }

}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        state = SearchUiState(),
        sideEffectFlow = null,
        onIntent = {},
        onNavigationRequested = {}
    )
}