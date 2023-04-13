package com.plznoanr.lol_usersearch_compose.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plznoanr.lol_usersearch_compose.ui.feature.main.MainContract
import com.plznoanr.lol_usersearch_compose.ui.feature.main.MainViewModel
import com.plznoanr.lol_usersearch_compose.ui.feature.main.composables.MainScreen
import com.plznoanr.lol_usersearch_compose.ui.navigation.navigateToSearch
import com.plznoanr.lol_usersearch_compose.ui.navigation.navigateToSpectator

@Composable
fun MainDestination(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    MainScreen(
        state = viewModel.state.value,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::postIntent,
    ) {
        when (it) {
            is MainContract.SideEffect.Navigation.ToSearch -> navController.navigateToSearch()
            is MainContract.SideEffect.Navigation.ToSpectator -> navController.navigateToSpectator(it.name)
        }

    }
}