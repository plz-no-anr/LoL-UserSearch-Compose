package com.plz.no.anr.lol_usersearch_compose.ui.feature.summoner

import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plznoanr.domain.model.Summoner

class SummonerContract : BaseContract() {

    data class UiState(
        val data: Summoner?,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState

    sealed class Event : ViewEvent {

        object OnLoad : Event()

        sealed class Navigation : Event() {
            object Back : Navigation()
        }

    }

    sealed class Effect : ViewSideEffect {

        data class Toast(val msg: String) : Effect()

        sealed class Navigation : Effect() {
            object Back : Navigation()
        }

    }

}