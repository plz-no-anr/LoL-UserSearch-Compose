package com.plznoanr.lol.feature.home

import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.summoner.DeleteAllSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.DeleteSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.GetSummonerListUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.SaveBookmarkIdUseCase
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.mvibase.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface SummonerState {

    data object Init : SummonerState

    data class Success(val list: PersistentList<Summoner>) : SummonerState

}

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSummonerListUseCase: GetSummonerListUseCase,
    private val deleteAllSummonerUseCase: DeleteAllSummonerUseCase,
    private val deleteSummonerUseCase: DeleteSummonerUseCase,
    private val saveBookmarkIdUseCase: SaveBookmarkIdUseCase
) : MviViewModel<UiState, Event, SideEffect>() {

    private val summonerList: StateFlow<SummonerState> = getSummonerListUseCase()
        .map {
            SummonerState.Success(it.toPersistentList())
        }.stateIn(
            scope = viewModelScope,
            initialValue = SummonerState.Init,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    override val uiState: StateFlow<UiState>

    init {
        val initialState = UiState()
        uiState = eventFlow
            .onStart { emit(OnInit) }
            .sendEvent {
                when (it) {
                    is OnBookmark -> saveBookmarkIdUseCase(it.summonerId)
                    is OnDeleteAll -> deleteAllSummonerUseCase()
                    is OnDelete -> deleteSummonerUseCase(it.name)
                    else -> return@sendEvent
                }
            }.eventFilter()
            .combine(summonerList) { event, list ->
                event to list
            }.scan(initialState) { state, pair ->
                when (pair.second) {
                    is SummonerState.Init -> when (pair.first) {
                        is OnRefresh -> state.copy(
                            isRefreshing = true
                        )

                        is OnInit -> state.copy(
                            isLoading = true
                        )

                        is OnNextPage -> state.copy(
                            loadNextPage = true
                        )

                        else -> state
                    }

                    is SummonerState.Success -> state.copy(
                        isLoading = false,
                        isRefreshing = false,
                        loadNextPage = false,
                        summonerList = (pair.second as SummonerState.Success).list
                    )
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = initialState,
                started = SharingStarted.Eagerly
            )
    }

    private fun Flow<Event>.eventFilter() = filter {
        it is OnInit || it is OnRefresh || it is OnNextPage
    }

}