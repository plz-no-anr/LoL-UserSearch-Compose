package com.plznoanr.lol.feature.home

import com.plznoanr.lol.core.model.Summoner
import kotlinx.collections.immutable.PersistentList

data class HomeUiState(
    val data: PersistentList<Summoner>? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

sealed interface HomeIntent {

    data object OnLoadData : HomeIntent

    data object OnRefresh : HomeIntent

    data object OnLoadMore : HomeIntent

    sealed interface Summoner : HomeIntent {
        data class OnDelete(val name: String) : Summoner
        data object OnDeleteAll : Summoner
    }

}

