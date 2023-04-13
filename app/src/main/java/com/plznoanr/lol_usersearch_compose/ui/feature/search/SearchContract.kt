package com.plznoanr.lol_usersearch_compose.ui.feature.search

import com.plznoanr.domain.model.Search
import com.plznoanr.lol_usersearch_compose.ui.base.BaseContract

sealed class SearchContract : BaseContract() {

    data class State(
        val data: List<Search>,
        val isLoading: Boolean,
        val error: String?
    ) : BaseContract.State {

            companion object {
                fun initial() = State(
                    data = emptyList(),
                    isLoading = false,
                    error = null
                )
            }
    }

    sealed class Intent :
        BaseContract.Intent {

        object OnLoad : Intent()

        object Refresh : Intent()

        sealed class Summoner : Intent() {
            data class OnSearch(val name: String) : Summoner()
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