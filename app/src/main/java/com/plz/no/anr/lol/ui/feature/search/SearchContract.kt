package com.plz.no.anr.lol.ui.feature.search

import com.plz.no.anr.lol.domain.model.Search
import com.plz.no.anr.lol.ui.base.BaseContract

sealed class SearchContract : BaseContract() {

    data class State(
        val data: List<Search>? = null,
        val name: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    ) : BaseContract.State

    sealed class Intent :
        BaseContract.Intent {

        object OnLoad : Intent()

        object Refresh : Intent()

        sealed class Summoner : Intent() {
            data class OnSearch(val name: String) : Summoner()

            data class OnNameChanged(val name: String) : Summoner()
        }

        sealed class Search : Intent() {
            data class OnDelete(val name: String) : Search()
            object OnDeleteAll : Search()
        }

        sealed class Navigation : Intent() {
            object Back : Navigation()
        }
    }

    sealed class SideEffect :
        BaseContract.SideEffect {

        data class Toast(val msg: String) : SideEffect()

        sealed class Navigation : SideEffect() {
            object Back : Navigation()
            data class ToSummoner(val name: String) : Navigation()
        }
    }

}