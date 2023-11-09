package com.plznoanr.lol.feature.spectator

import com.plznoanr.lol.core.model.Spectator

data class SpectatorUiState(
    val data: Spectator? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface SpectatorIntent {

    data object OnLoad : SpectatorIntent

    sealed interface Navigation : SpectatorIntent {
        data object Back : Navigation
    }
}

