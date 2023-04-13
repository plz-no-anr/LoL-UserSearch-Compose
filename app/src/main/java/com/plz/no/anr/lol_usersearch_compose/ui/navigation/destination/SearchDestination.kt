package com.plz.no.anr.lol_usersearch_compose.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plz.no.anr.lol_usersearch_compose.ui.feature.search.SearchContract
import com.plz.no.anr.lol_usersearch_compose.ui.feature.search.SearchViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.feature.search.composables.SearchScreen
import com.plz.no.anr.lol_usersearch_compose.ui.navigation.navigateToSummoner

@Composable
fun SearchDestination(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    SearchScreen(
        state = viewModel.uiState.value,
        sideEffectFlow = viewModel.sideEffect,
        onIntent = viewModel::setIntent,
        onNavigationRequested = {
            when (it) {
                is SearchContract.SideEffect.Navigation.ToSummoner -> navController.navigateToSummoner(it.name)
                is SearchContract.SideEffect.Navigation.Back -> navController.popBackStack()
            }
        }
    )

}