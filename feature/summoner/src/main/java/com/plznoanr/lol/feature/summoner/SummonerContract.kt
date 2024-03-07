package com.plznoanr.lol.feature.summoner

import androidx.compose.runtime.Stable
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.mvibase.MviEvent
import com.plznoanr.lol.core.mvibase.MviSideEffect
import com.plznoanr.lol.core.mvibase.MviState

@Stable
data class UiState(
    val summoner: Summoner? = null,
    val isLoading: Boolean = true,
    val errorMsg: String? = null
): MviState

sealed interface Event: MviEvent
data object OnLoad: Event
data class OnBookmark(val id: String): Event
data object OnBackPress: Event

data class OnWatch(val summonerId: String): Event

sealed interface SideEffect: MviSideEffect
data class NavigateToSpectator(val summonerId: String): SideEffect
data object OnBack: SideEffect
data class ShowSnackbar(val message: String): SideEffect
