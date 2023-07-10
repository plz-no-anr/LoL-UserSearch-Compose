package com.plz.no.anr.lol.ui.feature.main

import com.plz.no.anr.lol.domain.model.Profile
import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.ui.base.BaseContract

class HomeContract : BaseContract() {

    data class State(
        val data: List<Summoner>,
        val profile: Profile?,
        val key: String?,
        val isLoading: Boolean,
        val isRefreshing: Boolean,
        val error: String?
    ) : BaseContract.State {
            companion object {
                fun initial() = State(
                    data = emptyList(),
                    profile = null,
                    key = null,
                    isLoading = false,
                    isRefreshing = false,
                    error = null
                )
            }
    }

    sealed class Intent : BaseContract.Intent {

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

    sealed class SideEffect : BaseContract.SideEffect {

        data class Toast(val message: String) : SideEffect()

        object MoveGetApiKey : SideEffect()

        sealed class Navigation : SideEffect() {
            object ToSearch : Navigation()
            data class ToSpectator(val name: String) : Navigation()
        }

    }
}