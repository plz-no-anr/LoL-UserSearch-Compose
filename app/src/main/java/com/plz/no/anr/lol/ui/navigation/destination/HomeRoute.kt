package com.plz.no.anr.lol.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.plz.no.anr.lol.ui.feature.home.HomeContract
import com.plz.no.anr.lol.ui.feature.home.HomeViewModel
import com.plz.no.anr.lol.ui.feature.home.composables.HomeScreen
import com.plz.no.anr.lol.ui.navigation.navigateToSearch
import com.plz.no.anr.lol.ui.navigation.navigateToSpectator

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
            is HomeContract.SideEffect.Navigation.ToSearch -> navController.navigateToSearch()
            is HomeContract.SideEffect.Navigation.ToSpectator -> navController.navigateToSpectator(it.name)
        }

    }
}