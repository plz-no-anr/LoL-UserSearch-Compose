package com.plznoanr.lol.feature.home

import androidx.compose.runtime.Immutable
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.mvibase.MviEvent
import com.plznoanr.lol.core.mvibase.MviSideEffect
import com.plznoanr.lol.core.mvibase.MviState
import kotlinx.collections.immutable.PersistentList

@Immutable
data class UiState(
    val summonerList: PersistentList<Summoner>? = null,
    val isLoading: Boolean = false,
    val loadNextPage: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
) : MviState

sealed interface Event: MviEvent

data object OnInit : Event

data object OnRefresh : Event

data object OnNextPage : Event

data class OnSummonerClick(val summonerName: String) : Event

data class OnBookmark(val summonerId: String) : Event

data class OnDelete(val name: String) : Event

data object OnDeleteAll : Event

sealed interface SideEffect : MviSideEffect

