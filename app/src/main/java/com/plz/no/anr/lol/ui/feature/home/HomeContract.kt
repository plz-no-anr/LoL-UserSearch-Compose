package com.plz.no.anr.lol.ui.feature.home

import com.plz.no.anr.lol.domain.model.Profile
import com.plz.no.anr.lol.domain.model.Summoner
import plznoanr.coma.core.ComaContract

class HomeContract : ComaContract() {

    data class State(
        val data: List<Summoner>? = null,
        val profile: Profile? = null,
        val key: String? = null,
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val error: String? = null
    ) : ComaContract.State

    sealed class Intent : ComaContract.Intent {

        object OnLoad : Intent()

        object OnSearch : Intent()

        object Refresh : Intent()

        sealed class Summoner : Intent() {
            data class OnDelete(val name: String) : Summoner()
            object OnDeleteAll: Summoner()
        }

        sealed class Profile : Intent() {
            data class OnAdd(val profile: com.plz.no.anr.lol.domain.model.Profile) : Profile()
        }

        sealed class Spectator : Intent() {
            data class OnWatch(val name: String) : Spectator()

        }

        sealed class Key : Intent() {

            object OnGet : Key()
            data class OnAdd(val key: String) : Key()
            object OnDelete : Key()
        }

    }

    sealed class SideEffect : ComaContract.SideEffect {

        data class Toast(val message: String) : SideEffect()

        object MoveGetApiKey : SideEffect()

        sealed class Navigation : SideEffect() {
            object ToSearch : Navigation()
            data class ToSpectator(val name: String) : Navigation()
        }

    }
}