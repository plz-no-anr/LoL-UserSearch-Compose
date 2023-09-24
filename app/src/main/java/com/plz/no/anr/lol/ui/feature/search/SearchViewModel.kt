package com.plz.no.anr.lol.ui.feature.search

import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol.domain.usecase.search.DeleteAllSearchUseCase
import com.plz.no.anr.lol.domain.usecase.search.DeleteSearchUseCase
import com.plz.no.anr.lol.domain.usecase.search.GetSearchListUseCase
import com.plz.no.anr.lol.ui.feature.search.SearchContract.Intent
import com.plz.no.anr.lol.ui.feature.search.SearchContract.SideEffect
import com.plz.no.anr.lol.ui.feature.search.SearchContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import plznoanr.coma.core.ComaViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchListUseCase: GetSearchListUseCase,
    private val deleteSearchUseCase: DeleteSearchUseCase,
    private val deleteSearchAllUseCase: DeleteAllSearchUseCase
) : ComaViewModel<State, Intent, SideEffect>() {

    override fun setInitialState(): State = State()

    override fun handleIntents(intent: Intent) {
        when (intent) {
            is Intent.OnLoad -> getSearch()
            is Intent.Refresh -> {}
            is Intent.Summoner.OnSearch -> {
                if (intent.name.isNotEmpty()) {
                    postSideEffect { SideEffect.Navigation.ToSummoner(intent.name.trim()) }
                }
            }

            is Intent.Navigation.Back -> postSideEffect { SideEffect.Navigation.Back }
            is Intent.Search.OnDelete -> deleteSearch(intent.name)
            is Intent.Search.OnDeleteAll -> deleteAll()
            is Intent.Summoner.OnNameChanged -> reduce { copy(name = intent.name) }
        }
    }

    private fun getSearch() {
        getSearchListUseCase(Unit)
            .onStart { reduce { copy(isLoading = true) } }
            .onEach { result ->
                result.onSuccess {
                    reduce {
                        copy(
                            data = it.asReversed(),
                            isLoading = false
                        )
                    }
                }.onFailure {
                    reduce {
                        copy(
                            error = it.message,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun deleteSearch(name: String) {
        deleteSearchUseCase(name)
            .onStart { reduce { copy(isLoading = true) } }
            .onEach { result ->
                result.onSuccess {
                    reduce {
                        copy(
                            data = data?.filter { it.name != name },
                            isLoading = false
                        )
                    }
                }.onFailure {
                    reduce {
                        copy(
                            error = it.message,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun deleteAll() {
        deleteSearchAllUseCase(Unit)
            .onStart { reduce { copy(isLoading = true) } }
            .onEach { result ->
                result.onSuccess {
                    reduce {
                        copy(
                            data = emptyList(),
                            isLoading = false
                        )
                    }
                }.onFailure {
                    reduce {
                        copy(
                            error = it.message,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

}