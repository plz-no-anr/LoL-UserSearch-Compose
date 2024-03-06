package com.plznoanr.lol.feature.bookmark

import androidx.compose.runtime.Stable
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.mvibase.MviEvent
import com.plznoanr.lol.core.mvibase.MviSideEffect
import com.plznoanr.lol.core.mvibase.MviState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class UiState(
    val bookmarkList: PersistentList<Summoner> = persistentListOf(),
): MviState

sealed interface Event: MviEvent

data class OnBookmark(val id: String): Event
data object OnNextPage: Event

//sealed interface BookmarkUiState {
//
//    @Stable
//    data class Success(
//        val data: PersistentList<Summoner>
//    ) : BookmarkUiState
//
//    data object Loading : BookmarkUiState
//
//    data class Error(val error: String?) : BookmarkUiState
//
//}

sealed interface SideEffect: MviSideEffect