package com.plznoanr.lol.feature.home

import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.summoner.DeleteAllSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.DeleteSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.GetSummonerListUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.SaveBookmarkIdUseCase
import com.plznoanr.lol.core.mvibase.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSummonerListUseCase: GetSummonerListUseCase,
    private val deleteAllSummonerUseCase: DeleteAllSummonerUseCase,
    private val deleteSummonerUseCase: DeleteSummonerUseCase,
    private val saveBookmarkIdUseCase: SaveBookmarkIdUseCase
) : MviViewModel<UiState, Event, SideEffect>() {

    private val summonerList: StateFlow<SummonerState> =
        getSummonerListUseCase()
            .map {
                SummonerState.Success(it.toPersistentList())
            }.stateIn(
                scope = viewModelScope,
                initialValue = SummonerState.Loading,
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
            }
            .eventFilter()
            .combine(summonerList) { event, summonerState ->
                summonerState to event
            }.scan(initialState) { uiState, summonerState, event ->
                summonerState.reduceState(uiState, event)
            }.stateIn(
                scope = viewModelScope,
                initialValue = initialState,
                started = SharingStarted.Eagerly
            )
    }

    private fun Flow<Event>.eventFilter() = filter {
        it is OnInit || it is OnRefresh || it is OnNextPage
    }

    private fun Flow<Pair<SummonerState, Event>>.scan(
        initial: UiState,
        operation: suspend (accumulator: UiState, summonerState: SummonerState, event: Event) -> UiState
    ): Flow<UiState> = flow {
        var accumulator: UiState = initial
        emit(accumulator)
        collect { value ->
            accumulator = operation(accumulator, value.first, value.second)
            emit(accumulator)
        }
    }

}