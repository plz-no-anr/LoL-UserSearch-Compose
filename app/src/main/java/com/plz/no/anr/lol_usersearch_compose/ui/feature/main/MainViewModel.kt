package com.plz.no.anr.lol_usersearch_compose.ui.feature.main

import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.model.Summoner
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class MainContract : BaseContract() {

    data class UiState(
        val data: List<Summoner>,
        val profile: Profile? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnLoad : Event()
        object OnSearchClick : Event()
        object Refresh : Event()
    }

    sealed class Effect : ViewSideEffect {

        sealed class Navigation : Effect() {
            object ToSearch : Navigation()
        }

    }
}

@HiltViewModel
class MainViewModel @Inject constructor(

) : BaseViewModel<MainContract.UiState, MainContract.Event, MainContract.Effect>() {

    override fun setInitialState(): MainContract.UiState = MainContract.UiState(
        data = emptyList()
    )

    override fun handleEvents(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnLoad -> {}
            is MainContract.Event.OnSearchClick -> {
                setEffect { MainContract.Effect.Navigation.ToSearch }
            }
            is MainContract.Event.Refresh -> {}
        }
    }


}