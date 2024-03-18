package com.plznoanr.lol.feature.home

import androidx.compose.runtime.Immutable
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.mvibase.MviEvent
import com.plznoanr.lol.core.mvibase.MviSideEffect
import com.plznoanr.lol.core.mvibase.MviState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class UiState(
    val summonerList: PersistentList<Summoner> = persistentListOf(),
    val isLoading: Boolean = false,
    val isLoadNextPage: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
) : MviState

sealed interface SummonerState {

    data object Loading : SummonerState

    data class Success(val list: PersistentList<Summoner>) : SummonerState

}

internal fun SummonerState.reduceState(
    initialState: UiState,
    event: Event
): UiState = when (this) {
    is SummonerState.Loading -> event.reduceState(initialState)
    is SummonerState.Success -> initialState.copy(
        isLoading = false,
        isRefreshing = false,
        isLoadNextPage = false,
        summonerList = list
    )
}

@Immutable
sealed interface Event : MviEvent {
    data object OnInit : Event

    data object OnRefresh : Event

    data object OnNextPage : Event

    data class OnSummonerClick(val summonerName: String) : Event

    data class OnBookmark(val summonerId: String) : Event

    data class OnDelete(val name: String) : Event

    data object OnDeleteAll : Event
}

private fun Event.reduceState(state: UiState): UiState = when (this) {
    is Event.OnInit -> state.copy(
        isLoading = true
    )

    is Event.OnRefresh -> state.copy(
        isRefreshing = true
    )

    is Event.OnNextPage -> state.copy(
        isLoadNextPage = true
    )

    else -> state
}

sealed interface SideEffect : MviSideEffect

