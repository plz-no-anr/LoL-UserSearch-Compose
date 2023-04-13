package com.plz.no.anr.lol_usersearch_compose.ui.feature.search

import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plznoanr.domain.model.Search

sealed class SearchContract : BaseContract() {

    data class UiState(
        val data: List<Search>,
        val isLoading: Boolean,
        val error: String?
    ) : BaseContract.UiState {

            companion object {
                fun initial() = UiState(
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