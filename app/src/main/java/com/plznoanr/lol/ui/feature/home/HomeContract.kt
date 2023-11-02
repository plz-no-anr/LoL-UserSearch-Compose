package com.plznoanr.lol.ui.feature.home

import com.plznoanr.lol.core.model.Profile
import com.plznoanr.lol.core.model.Summoner
import plznoanr.coma.core.ComaContract

data class HomeUiState(
    val data: List<Summoner>? = null,
    val profile: Profile? = null,
    val key: String? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
) : ComaContract.State

sealed class HomeIntent : ComaContract.Intent {

    data object OnLoad : HomeIntent()

    data object OnSearch : HomeIntent()

    data object Refresh : HomeIntent()

    sealed class Summoner : HomeIntent() {
        data class OnDelete(val name: String) : Summoner()
        data object OnDeleteAll : Summoner()
    }

    sealed class Profile : HomeIntent() {
        data class OnAdd(val profile: com.plznoanr.lol.core.model.Profile) : Profile()
    }

    sealed class Spectator : HomeIntent() {
        data class OnWatch(val summonerId: String) : Spectator()

    }

    sealed class Key : HomeIntent() {

        data object OnGet : Key()
        data class OnAdd(val key: String) : Key()
        data object OnDelete : Key()
    }

}

sealed class HomeSideEffect : ComaContract.SideEffect {

    data class Toast(val message: String) : HomeSideEffect()

    data object MoveGetApiKey : HomeSideEffect()

    sealed class Navigation : HomeSideEffect() {
        data object ToSearch : Navigation()
        data class ToSpectator(val summonerId: String) : Navigation()
    }

}
