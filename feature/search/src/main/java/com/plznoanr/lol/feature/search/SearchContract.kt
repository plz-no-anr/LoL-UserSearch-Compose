package com.plznoanr.lol.feature.search

import androidx.compose.runtime.Stable
import com.plznoanr.lol.core.model.Search
import com.plznoanr.lol.core.mvibase.MviEvent
import com.plznoanr.lol.core.mvibase.MviSideEffect
import com.plznoanr.lol.core.mvibase.MviState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class UiState(
    val data: PersistentList<Search> = persistentListOf(),
    val query: String = "",
    val isActive: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
): MviState

sealed interface Event: MviEvent
data class OnSearch(val fullName: String): Event
data class OnActiveChange(val isActive: Boolean): Event
data class OnQueryChange(val query: String): Event
data class OnDelete(val name: String): Event
data object OnDeleteAll: Event

sealed interface SideEffect: MviSideEffect

data class NavigateToSummoner(val name: String, val tag: String): SideEffect
data class ShowSnackbar(val message: String): SideEffect


//sealed interface SearchIntent {
//
//    data object OnLoad : SearchIntent
//
//    data object Refresh : SearchIntent
//
//    sealed interface Summoner : SearchIntent {
//        data class OnSearch(val name: String) : Summoner
//
//        data class OnNameChanged(val name: String) : Summoner
//    }
//
//    sealed interface Search : SearchIntent {
//        data class OnDelete(val name: String) : Search
//        data object OnDeleteAll : Search
//    }
//
//}
//
//sealed interface SearchSideEffect {
//
//    data class Toast(val msg: String) : SearchSideEffect
//
//    sealed interface Navigation : SearchSideEffect {
//        data class ToSummoner(val name: String) : Navigation
//    }
//}

