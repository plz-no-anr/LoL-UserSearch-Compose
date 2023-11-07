package com.plznoanr.lol.feature.search

import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.search.DeleteAllSearchUseCase
import com.plznoanr.lol.core.domain.usecase.search.DeleteSearchUseCase
import com.plznoanr.lol.core.domain.usecase.search.GetSearchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import plznoanr.coma.core.ComaViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchListUseCase: GetSearchListUseCase,
    private val deleteSearchUseCase: DeleteSearchUseCase,
    private val deleteSearchAllUseCase: DeleteAllSearchUseCase
) : ComaViewModel<SearchUiState, SearchIntent, SearchSideEffect>() {

    override fun setInitialState(): SearchUiState = SearchUiState()

    override fun handleIntents(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.OnLoad -> getSearch()
            is SearchIntent.Refresh -> {}
            is SearchIntent.Summoner.OnSearch -> {
                if (intent.name.isNotEmpty()) {
                    postSideEffect { SearchSideEffect.Navigation.ToSummoner(intent.name.trim()) }
                }
            }

            is SearchIntent.Navigation.Back -> postSideEffect { SearchSideEffect.Navigation.Back }
            is SearchIntent.Search.OnDelete -> deleteSearch(intent.name)
            is SearchIntent.Search.OnDeleteAll -> deleteAll()
            is SearchIntent.Summoner.OnNameChanged -> reduce { copy(name = intent.name) }
        }
    }

    private fun getSearch() {
        getSearchListUseCase()
            .onStart { reduce { copy(isLoading = true) } }
            .onEach { result ->
                reduce {
                    copy(
                        data = result,
                        isLoading = false
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun deleteSearch(name: String) {
        viewModelScope.launch {
            deleteSearchUseCase(name)
        }
    }

    private fun deleteAll() {
        viewModelScope.launch {
            deleteSearchAllUseCase()
        }
    }

}