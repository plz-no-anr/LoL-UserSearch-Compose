package com.plz.no.anr.lol_usersearch_compose.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plz.no.anr.lol_usersearch_compose.ui.feature.search.SearchViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.feature.search.composables.SearchScreen

@Composable
fun SearchDestination(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {

    SearchScreen(
        state = viewModel.uiState.value,
        effectFlow = viewModel.effect,
        onEvent = viewModel::setEvent,
        onNavigationRequested = {  }
    )

}