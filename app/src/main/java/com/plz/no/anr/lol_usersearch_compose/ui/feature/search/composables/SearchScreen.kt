package com.plz.no.anr.lol_usersearch_compose.ui.feature.search.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plz.no.anr.lol_usersearch_compose.ui.base.SIDE_EFFECTS_KEY
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.AppProgressBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.ErrorScreen
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.TopAppBar
import com.plz.no.anr.lol_usersearch_compose.ui.feature.search.SearchContract
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
            state.error != null -> ErrorScreen(errMsg = state.error) {}
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    data: List<Search>,
    name: String,
    onNameChange: (String) -> Unit,
    onEvent: (SearchContract.Event) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text( text = stringResource(id = R.string.summoner_name) ) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            trailingIcon = {
                IconButton(
                    onClick = { onEvent(SearchContract.Event.Summoner.OnSearch(name)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),

        )

        TextButton(
            modifier = Modifier
                .align(Alignment.End),
            onClick = { onEvent(SearchContract.Event.Search.OnDeleteAll) },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color.Black
            )
        ) {
            Text(text = stringResource(id = R.string.delete_all))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            items(data) { search ->
                SearchItem(
                    data = search,
                    onSearch = { onEvent(SearchContract.Event.Summoner.OnSearch(search.name)) },
                    onDelete = { onEvent(SearchContract.Event.Search.OnDelete(search.name)) }
                )
            }
        }

    }

}


@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        state = SearchContract.UiState(
            data = (0..30).map { Search( "name $it", "2023-03-$it") }.asReversed(),
        ),
        effectFlow = null,
        onEvent = {},
        onNavigationRequested = {}
    )
}