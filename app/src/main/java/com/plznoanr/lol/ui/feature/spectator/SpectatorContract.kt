package com.plznoanr.lol.ui.feature.spectator

import com.plznoanr.model.Spectator
import plznoanr.coma.core.ComaContract

data class SpectatorUiState(
    val data: Spectator? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) : ComaContract.State

sealed class SpectatorIntent : ComaContract.Intent {

    data object OnLoad : SpectatorIntent()

    sealed class Navigation : SpectatorIntent() {
        data object Back : Navigation()
    }
}

sealed class SpectatorSideEffect : ComaContract.SideEffect {
    data class Toast(val msg: String) : SpectatorSideEffect()

    sealed class Navigation : SpectatorSideEffect() {
        data object Back : Navigation()
    }

}

