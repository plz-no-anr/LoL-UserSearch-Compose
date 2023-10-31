package com.plznoanr.lol.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.plznoanr.lol.ui.feature.home.HomeSideEffect
import com.plznoanr.lol.ui.feature.home.HomeViewModel
import com.plznoanr.lol.ui.feature.home.composables.HomeScreen
import com.plznoanr.lol.ui.navigation.navigateToSearch
import com.plznoanr.lol.ui.navigation.navigateToSpectator

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::postIntent,
    ) {
        when (it) {
            is HomeSideEffect.Navigation.ToSearch -> navController.navigateToSearch()
            is HomeSideEffect.Navigation.ToSpectator -> navController.navigateToSpectator(it.summonerId)
        }

    }
}