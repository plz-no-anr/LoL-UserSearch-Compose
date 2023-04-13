package com.plz.no.anr.lol_usersearch_compose.ui.feature.search

import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plz.no.anr.lol_usersearch_compose.ui.feature.search.SearchContract.*
import com.plznoanr.domain.usecase.search.DeleteAllSearchUseCase
import com.plznoanr.domain.usecase.search.DeleteSearchUseCase
import com.plznoanr.domain.usecase.search.GetSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val deleteSearchUseCase: DeleteSearchUseCase,
    private val deleteSearchAllUseCase: DeleteAllSearchUseCase
) : BaseViewModel<UiState, Intent, SideEffect>() {

    override fun setInitialState(): UiState = UiState.initial()

    override fun handleIntents(intent: Intent) {
        when (intent) {
            is Intent.OnLoad -> getSearch()
            is Intent.Refresh -> {}
            is Intent.Summoner.OnSearch -> {
                if (intent.name.isNotEmpty()) {
                    setEffect { SideEffect.Navigation.ToSummoner(intent.name.trim()) }
                }
            }
            is Intent.Navigation.Back -> setEffect { SideEffect.Navigation.Back }
            is Intent.Search.OnDelete -> deleteSearch(intent.name)
            is Intent.Search.OnDeleteAll -> deleteAll()
        }
    }

    private fun getSearch() {
        viewModelScope.launch {
            getSearchUseCase(Unit)
                .onStart { setState { copy(isLoading = true) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                data = it.asReversed(),
                                isLoading = false
                            )
                        }
                    }.onFailure {
                        setState {
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
                .onStart { setState { copy(isLoading = true) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                data = data.filter { it.name != name },
                                isLoading = false
                            )
                        }
                    }.onFailure {
                        setState {
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
                .onStart { setState { copy(isLoading = true) } }
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                data = emptyList(),
                                isLoading = false
                            )
                        }
                    }.onFailure {
                        setState {
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