package com.plznoanr.lol.feature.bookmark

import androidx.compose.runtime.Immutable
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.mvibase.MviEvent
import com.plznoanr.lol.core.mvibase.MviSideEffect
import com.plznoanr.lol.core.mvibase.MviUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class UiState(
    val bookmarkList: PersistentList<Summoner> = persistentListOf(),
): MviUiState

@Immutable
sealed interface Event: MviEvent {
    data class OnBookmark(val id: String): Event
    data object OnClear: Event
}

sealed interface SideEffect: MviSideEffect