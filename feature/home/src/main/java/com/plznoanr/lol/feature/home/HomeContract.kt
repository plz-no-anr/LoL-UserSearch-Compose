package com.plznoanr.lol.feature.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.plznoanr.lol.core.common.model.AppError
import com.plznoanr.lol.core.domain.usecase.summoner.SummonerState
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.mvibase.MviEvent
import com.plznoanr.lol.core.mvibase.MviSideEffect
import com.plznoanr.lol.core.mvibase.MviState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Stable
data class UiState(
    val summonerList: PersistentList<Summoner> = persistentListOf(),
    val isLoading: Boolean = false,
    val isLoadNextPage: Boolean = false,
    val isRefreshing: Boolean = false,
    val isSortedBookmark: Boolean = false,
    val error: AppError? = null
) : MviState

internal fun SummonerState.reduceState(
    initialState: UiState,
    event: Event
): UiState = when (this) {
    is SummonerState.Loading -> event.reduceState(initialState)
    is SummonerState.Success -> initialState.copy(
        isLoading = false,
        isRefreshing = false,
        isLoadNextPage = false,
        summonerList = list.toPersistentList()
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

    data class OnSortedBookmark(val sorted: Boolean) : Event
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

    is Event.OnSortedBookmark -> state.copy(
        isSortedBookmark = sorted
    )

    else -> state
}

sealed interface SideEffect : MviSideEffect

