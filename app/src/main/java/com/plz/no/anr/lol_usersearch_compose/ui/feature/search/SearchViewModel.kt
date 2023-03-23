package com.plz.no.anr.lol_usersearch_compose.ui.feature.search

import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseContract
import com.plz.no.anr.lol_usersearch_compose.ui.base.BaseViewModel
import com.plznoanr.domain.model.Search
import com.plznoanr.domain.usecase.search.DeleteAllSearchUseCase
import com.plznoanr.domain.usecase.search.DeleteSearchUseCase
import com.plznoanr.domain.usecase.search.GetSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val deleteSearchUseCase: DeleteSearchUseCase,
    private val deleteSearchAllUseCase: DeleteAllSearchUseCase
) : BaseViewModel<SearchContract.UiState, SearchContract.Event, SearchContract.Effect>() {

    override fun setInitialState(): SearchContract.UiState =
        SearchContract.UiState(data = emptyList())

    override fun handleEvents(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnLoad -> {
                getSearch()
            }
            is SearchContract.Event.Refresh -> {}
            is SearchContract.Event.Summoner.OnSearch -> {
                if (event.name.isNotEmpty()) {
                    setEffect { SearchContract.Effect.Navigation.ToSummoner(event.name.trim()) }
                }
            }
            is SearchContract.Event.Navigation.Back -> {
                setEffect { SearchContract.Effect.Navigation.Back }
            }
            is SearchContract.Event.Search.OnDelete -> {
                deleteSearch(event.name)
            }
            is SearchContract.Event.Search.OnDeleteAll -> {
                deleteAll()
            }
        }
    }

    private fun getSearch() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getSearchUseCase(Unit).collectLatest { result ->
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
            deleteSearchUseCase(name).collectLatest { result ->
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
            deleteSearchAllUseCase(Unit).collectLatest { result ->
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