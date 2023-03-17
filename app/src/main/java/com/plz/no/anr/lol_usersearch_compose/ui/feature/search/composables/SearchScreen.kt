package com.plz.no.anr.lol_usersearch_compose.ui.feature.search.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.plz.no.anr.lol_usersearch_compose.ui.feature.search.SearchContract
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchScreen(
    state: SearchContract.UiState,
    effectFlow: Flow<SearchContract.Effect>?,
    onEvent: (SearchContract.Event) -> Unit,
    onNavigationRequested: (SearchContract.Effect.Navigation) -> Unit,
    ) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red))

}


@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        state = SearchContract.UiState(
            data = emptyList()
        ),
        effectFlow = null,
        onEvent = {},
        onNavigationRequested = {}
    )
}