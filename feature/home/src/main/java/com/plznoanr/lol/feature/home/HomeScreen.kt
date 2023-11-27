package com.plznoanr.lol.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.common.model.parseError
import com.plznoanr.lol.core.designsystem.component.AppProgressBar
import com.plznoanr.lol.core.designsystem.component.error.ErrorScreen
import com.plznoanr.lol.core.model.Profile

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        onIntent = viewModel::postIntent,
    )
}

@Composable
internal fun HomeScreen(
    state: HomeUiState2,
    onIntent: (HomeIntent) -> Unit,
) {
    when(state) {
        is HomeUiState2.Loading -> AppProgressBar()
        is HomeUiState2.Error -> ErrorScreen(
            error = state.error.parseError()
        ) { onIntent(HomeIntent.OnLoadData) }
        is HomeUiState2.Data -> {
            HomeContent(
                data = state.data,
                isRefreshing = refreshState,
            ) { intent ->
                onIntent(intent)
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        state = HomeUiState2.Loading,
        onIntent = {},
    )
}


private fun getDummyProfile() = Profile(
    id = "id",
    name = "name",
    icon = "http://ddragon.leagueoflegends.com/cdn/13.6.1/img/profileicon/6.png",
    level = "100"
)
