package com.plz.no.anr.lol_usersearch_compose.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plz.no.anr.lol_usersearch_compose.ui.feature.main.MainContract
import com.plz.no.anr.lol_usersearch_compose.ui.feature.main.MainViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.feature.main.composables.MainScreen
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.navigateToSearch

@Composable
fun MainDestination(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    MainScreen(
        state = viewModel.uiState.value,
        effectFlow = viewModel.effect,
        onEvent = viewModel::setEvent,
    ) {
        when (it) {
            is MainContract.Effect.Navigation.ToSearch -> navController.navigateToSearch()
        }

    }
}