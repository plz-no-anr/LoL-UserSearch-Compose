package com.plz.no.anr.lol_usersearch_compose.ui.feature.search.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.plz.no.anr.lol_usersearch_compose.ui.base.SIDE_EFFECTS_KEY
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.AppProgressBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.error.ErrorScreen
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.TopAppBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.search.SearchContract
import com.plznoanr.data.model.common.parseError
import com.plznoanr.lol_usersearch_compose.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchContract.UiState,
    sideEffectFlow: Flow<SearchContract.SideEffect>?,
    onIntent: (SearchContract.Intent) -> Unit,
    onNavigationRequested: (SearchContract.SideEffect.Navigation) -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var name by remember { mutableStateOf("") }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        onIntent(SearchContract.Intent.OnLoad)
        sideEffectFlow?.onEach { sideEffect ->
            when (sideEffect) {
                is SearchContract.SideEffect.Toast -> snackbarHostState.showSnackbar(
                    message = sideEffect.msg,
                    duration = SnackbarDuration.Short
                )
                is SearchContract.SideEffect.Navigation.Back -> onNavigationRequested(sideEffect)
                is SearchContract.SideEffect.Navigation.ToSummoner -> onNavigationRequested(sideEffect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.search_title),
                isBackPressVisible = true,
                onBackPressed = { onIntent(SearchContract.Intent.Navigation.Back) }
            )
        }
    ) {
        when {
            state.isLoading -> { AppProgressBar() }
            state.error != null -> ErrorScreen(error = state.error.parseError()) {}
            else -> {
                SearchContent(
                    modifier = Modifier
                        .padding(it),
                    data = state.data,
                    name = name,
                    onNameChange = { summonerName -> name = summonerName },
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
        state = SearchContract.UiState.initial(),
        sideEffectFlow = null,
        onIntent = {},
        onNavigationRequested = {}
    )
}