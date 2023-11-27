package com.plznoanr.lol.feature.home

import androidx.compose.runtime.Stable
import com.plznoanr.lol.core.model.Summoner
import kotlinx.collections.immutable.PersistentList

data class HomeUiState(
    val data: PersistentList<Summoner>? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

sealed interface HomeUiState2 {
    data object Loading : HomeUiState2

    data object Refresh : HomeUiState2

    data class Error(val error: String? = null) : HomeUiState2

    @Stable
    data class Data(val data: PersistentList<Summoner>, val isRefreshing: Boolean = false) : HomeUiState2

}

sealed interface HomeIntent {

    data object OnLoadData : HomeIntent

    data object OnRefresh : HomeIntent

    data object OnLoadMore : HomeIntent

    sealed interface Summoner : HomeIntent {

        data class OnSummonerClick(val summonerName: String) : Summoner

        data class OnBookmark(val summonerId: String) : Summoner

        data class OnDelete(val name: String) : Summoner

        data object OnDeleteAll : Summoner
    }

}

