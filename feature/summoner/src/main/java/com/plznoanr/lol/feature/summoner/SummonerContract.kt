package com.plznoanr.lol.feature.summoner

import androidx.compose.runtime.Immutable
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

@Immutable
sealed interface Event: MviEvent {
    data object OnInit: Event
    data class OnBookmark(val id: String): Event
    data object OnBackClick: Event
    data class OnWatch(val summonerId: String): Event
}

sealed interface SideEffect: MviSideEffect
data class NavigateToSpectator(val summonerId: String): SideEffect
data object OnPopBack: SideEffect
data class ShowSnackbar(val message: String): SideEffect
