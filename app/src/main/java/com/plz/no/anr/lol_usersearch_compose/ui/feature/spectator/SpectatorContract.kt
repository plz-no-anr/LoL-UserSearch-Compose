package com.plz.no.anr.lol_usersearch_compose.ui.feature.spectator

import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plznoanr.domain.model.Spectator

class SpectatorContract : BaseContract() {

    data class UiState(
        val data: Spectator?,
        val isLoading: Boolean,
        val error: String?
    ) : BaseContract.UiState {

            companion object {
                fun initial() = UiState(
                    data = null,
                    isLoading = false,
                    error = null
                )
            }
    }

    sealed class Intent : BaseContract.Intent {

        object OnLoad : Intent()

        sealed class Navigation : Intent() {
            object Back : Navigation()
        }
    }

    sealed class SideEffect : BaseContract.SideEffect {
        data class Toast(val msg: String) : SideEffect()

        sealed class Navigation : SideEffect() {
            object Back : Navigation()
        }

    }

}