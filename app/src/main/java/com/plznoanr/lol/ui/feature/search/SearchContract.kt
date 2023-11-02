package com.plznoanr.lol.ui.feature.search

import com.plznoanr.lol.core.model.Search
import plznoanr.coma.core.ComaContract

data class SearchUiState(
    val data: List<Search>? = null,
    val name: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) : ComaContract.State

sealed class SearchIntent : ComaContract.Intent {

    data object OnLoad : SearchIntent()

    data object Refresh : SearchIntent()

    sealed class Summoner : SearchIntent() {
        data class OnSearch(val name: String) : Summoner()

        data class OnNameChanged(val name: String) : Summoner()
    }

    sealed class Search : SearchIntent() {
        data class OnDelete(val name: String) : Search()
        data object OnDeleteAll : Search()
    }

    sealed class Navigation : SearchIntent() {
        data object Back : Navigation()
    }
}

sealed class SearchSideEffect : ComaContract.SideEffect {

    data class Toast(val msg: String) : SearchSideEffect()

    sealed class Navigation : SearchSideEffect() {
        data object Back : Navigation()
        data class ToSummoner(val name: String) : Navigation()
    }
}

