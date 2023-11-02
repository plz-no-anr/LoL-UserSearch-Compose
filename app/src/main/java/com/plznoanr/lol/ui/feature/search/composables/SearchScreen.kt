package com.plznoanr.lol.ui.feature.search.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.plznoanr.lol.R
import com.plznoanr.lol.core.common.model.parseError
import com.plznoanr.lol.ui.feature.common.AppProgressBar
import com.plznoanr.lol.ui.feature.common.TopAppBar
import com.plznoanr.lol.ui.feature.common.error.ErrorScreen
import com.plznoanr.lol.ui.feature.search.SearchIntent
import com.plznoanr.lol.ui.feature.search.SearchSideEffect
import com.plznoanr.lol.ui.feature.search.SearchUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
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
            TopAppBar(
                title = stringResource(id = R.string.search_title),
                isBackPressVisible = true,
                onBackPressed = { onIntent(SearchIntent.Navigation.Back) }
            )
        }
    ) {
        when {
            state.isLoading -> AppProgressBar()
            state.error != null -> ErrorScreen(error = state.error.parseError()) {}
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