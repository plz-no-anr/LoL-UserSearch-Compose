package com.plznoanr.lol.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.plznoanr.lol.ui.feature.search.SearchSideEffect
import com.plznoanr.lol.ui.feature.search.SearchViewModel
import com.plznoanr.lol.ui.feature.search.composables.SearchScreen
import com.plznoanr.lol.ui.navigation.navigateToSummoner

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
        state = state,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::postIntent,
        onNavigationRequested = {
            when (it) {
                is SearchSideEffect.Navigation.ToSummoner -> navController.navigateToSummoner(it.name)
                is SearchSideEffect.Navigation.Back -> navController.popBackStack()
            }
        }
    )

}