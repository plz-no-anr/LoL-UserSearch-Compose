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
import com.plznoanr.domain.model.Search
import com.plznoanr.lol_usersearch_compose.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchContract.UiState,
    effectFlow: Flow<SearchContract.Effect>?,
    onEvent: (SearchContract.Event) -> Unit,
    onNavigationRequested: (SearchContract.Effect.Navigation) -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var name by remember { mutableStateOf("") }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        onEvent(SearchContract.Event.OnLoad)
        effectFlow?.onEach { effect ->
            when (effect) {
                is SearchContract.Effect.Toast -> snackbarHostState.showSnackbar(
                    message = effect.msg,
                    duration = SnackbarDuration.Short
                )
                is SearchContract.Effect.Navigation.Back -> onNavigationRequested(effect)
                is SearchContract.Effect.Navigation.ToSummoner -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.search_title),
                isBackPressVisible = true,
                onBackPressed = { onEvent(SearchContract.Event.Navigation.Back) }
            )
        }
    ) {
        when {
            state.isLoading -> { AppProgressBar() }
            state.error != null -> ErrorScreen(error = state.error.parseError()) {}
            else -> {
                SearchView(
                    modifier = Modifier
                        .padding(it),
                    data = state.data,
                    name = name,
                    onNameChange = { summonerName -> name = summonerName },
                    onEvent = onEvent,
                )
            }
        }

    }

}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        state = SearchContract.UiState(
            data = (0..30).map { Search( "name $it", "2023-03-$it") }.asReversed(),
        ),
        effectFlow = null,
        onEvent = {},
        onNavigationRequested = {}
    )
}