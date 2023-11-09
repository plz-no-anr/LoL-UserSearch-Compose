package com.plznoanr.lol.feature.search

import com.plznoanr.lol.core.model.Search

data class SearchUiState(
    val data: List<Search>? = null,
    val name: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface SearchIntent {

    data object OnLoad : SearchIntent

    data object Refresh : SearchIntent

    sealed interface Summoner : SearchIntent {
        data class OnSearch(val name: String) : Summoner

        data class OnNameChanged(val name: String) : Summoner
    }

    sealed interface Search : SearchIntent {
        data class OnDelete(val name: String) : Search
        data object OnDeleteAll : Search
    }

}

sealed interface SearchSideEffect {

    data class Toast(val msg: String) : SearchSideEffect

    sealed interface Navigation : SearchSideEffect {
        data class ToSummoner(val name: String) : Navigation
    }
}

