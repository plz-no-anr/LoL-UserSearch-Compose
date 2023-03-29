package com.plz.no.anr.lol_usersearch_compose.ui.feature.search

import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plznoanr.domain.model.Search

sealed class SearchContract : BaseContract() {

    data class UiState(
        val data: List<Search>,
        val isLoading: Boolean = true,
        val error: String? = null
    ) : ViewState

    sealed class Event : ViewEvent {

        object OnLoad : Event()

        object Refresh : Event()

        sealed class Summoner : Event() {
            data class OnSearch(val name: String) : Event()
        }

        sealed class Search : Event() {
            data class OnDelete(val name: String) : Search()
            object OnDeleteAll : Search()
        }

        object Navigation {
            object Back : Event()
        }
    }

    sealed class Effect : ViewSideEffect {

        data class Toast(val msg: String) : Effect()

        sealed class Navigation : Effect() {
            object Back : Navigation()
            data class ToSummoner(val name: String) : Navigation()
        }
    }

}