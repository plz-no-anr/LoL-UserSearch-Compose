package com.plz.no.anr.lol.ui.feature.search

import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol.domain.usecase.search.DeleteAllSearchUseCase
import com.plz.no.anr.lol.domain.usecase.search.DeleteSearchUseCase
import com.plz.no.anr.lol.domain.usecase.search.GetSearchUseCase
import com.plz.no.anr.lol.ui.base.BaseViewModel
import com.plz.no.anr.lol.ui.feature.search.SearchContract.Intent
import com.plz.no.anr.lol.ui.feature.search.SearchContract.SideEffect
import com.plz.no.anr.lol.ui.feature.search.SearchContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val deleteSearchUseCase: DeleteSearchUseCase,
    private val deleteSearchAllUseCase: DeleteAllSearchUseCase
) : BaseViewModel<State, Intent, SideEffect>() {

    override fun setInitialState(): State = State.initial()

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
        }
    }

    private fun getSearch() {
        viewModelScope.launch {
            getSearchUseCase(Unit)
                .onStart { reduce { copy(isLoading = true) } }
                .collect { result ->
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
                }
        }
    }

    private fun deleteSearch(name: String) {
        viewModelScope.launch {
            deleteSearchUseCase(name)
                .onStart { reduce { copy(isLoading = true) } }
                .collect { result ->
                    result.onSuccess {
                        reduce {
                            copy(
                                data = data.filter { it.name != name },
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
                }
        }
    }

    private fun deleteAll() {
        viewModelScope.launch {
            deleteSearchAllUseCase(Unit)
                .onStart { reduce { copy(isLoading = true) } }
                .collect { result ->
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
                }
        }
    }

}