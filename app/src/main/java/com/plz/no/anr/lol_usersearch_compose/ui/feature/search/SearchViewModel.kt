package com.plz.no.anr.lol_usersearch_compose.ui.feature.search

import androidx.lifecycle.SavedStateHandle
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plznoanr.domain.model.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class SearchContract : BaseContract() {

    data class UiState(
        val data: List<Search>,
        val isLoading: Boolean = false,
        val error: String? = null
    ): ViewState

    sealed class Event : ViewEvent {
        object Refresh : Event()
        object Navigation {
            object Back: Event()
        }
    }

    sealed class Effect : ViewSideEffect {
        object Save : Effect()

        data class Toast(val msg: String) : Effect()

        sealed class Navigation: Effect() {
            object Back: Navigation()
            data class To(val destination: Navigation) : Navigation()
        }
    }

}

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedState: SavedStateHandle
): BaseViewModel<SearchContract.UiState, SearchContract.Event, SearchContract.Effect>() {

    init {
        savedState.get<String>("id")
    }

    override fun setInitialState(): SearchContract.UiState = SearchContract.UiState(data = emptyList())

    override fun handleEvents(event: SearchContract.Event) {
    }




}