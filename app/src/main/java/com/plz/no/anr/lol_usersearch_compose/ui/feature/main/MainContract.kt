package com.plz.no.anr.lol_usersearch_compose.ui.feature.main

import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.model.Summoner

class MainContract : BaseContract() {

    data class UiState(
        val data: List<Summoner>,
        val profile: Profile?,
        val key: String?,
        val isLoading: Boolean,
        val isRefreshing: Boolean,
        val error: String?
    ) : ViewState {
            companion object {
                fun initial() = UiState(
                    data = emptyList(),
                    profile = null,
                    key = null,
                    isLoading = false,
                    isRefreshing = false,
                    error = null
                )
            }
    }

    sealed class Event : ViewEvent {

        object OnLoad : Event()

        object OnSearch : Event()

        object Refresh : Event()

        sealed class Summoner : Event() {
            data class OnDelete(val name: String) : Summoner()
            object OnDeleteAll: Summoner()
        }

        sealed class Profile : Event() {
            data class OnAdd(val profile: com.plznoanr.domain.model.Profile) : Profile()
        }

        sealed class Spectator : Event() {
            data class OnWatch(val name: String) : Spectator()

        }

        sealed class Key : Event() {

            object OnGet : Key()
            data class OnAdd(val key: String) : Key()
            object OnDelete : Key()
        }

    }

    sealed class Effect : ViewSideEffect {

        data class Toast(val message: String) : Effect()

        object MoveGetApiKey : Effect()

        sealed class Navigation : Effect() {
            object ToSearch : Navigation()
            data class ToSpectator(val name: String) : Navigation()
        }

    }
}