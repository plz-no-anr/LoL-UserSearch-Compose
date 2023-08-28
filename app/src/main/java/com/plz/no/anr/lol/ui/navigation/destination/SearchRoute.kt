package com.plz.no.anr.lol.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.plz.no.anr.lol.ui.feature.search.SearchContract
import com.plz.no.anr.lol.ui.feature.search.SearchViewModel
import com.plz.no.anr.lol.ui.feature.search.composables.SearchScreen
import com.plz.no.anr.lol.ui.navigation.navigateToSummoner

@Composable
fun SearchDestination(
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
                is SearchContract.SideEffect.Navigation.ToSummoner -> navController.navigateToSummoner(it.name)
                is SearchContract.SideEffect.Navigation.Back -> navController.popBackStack()
            }
        }
    )

}