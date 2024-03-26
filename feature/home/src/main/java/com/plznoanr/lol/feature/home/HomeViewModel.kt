package com.plznoanr.lol.feature.home

import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.summoner.DeleteAllSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.DeleteSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.GetSummonerListUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.SaveBookmarkIdUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.SummonerState
import com.plznoanr.lol.core.mvibase.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
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

    private val userEvent = MutableSharedFlow<Event>()
    private val userEventFlow = userEvent.flatMapLatest {
        getSummonerListUseCase(
            isSortedBookmark = if (it is Event.OnSortedBookmark) it.sorted else false,
            isClear = it is Event.OnRefresh
        )
    }

    private val summonerListState: StateFlow<SummonerState> =
        merge(
            getSummonerListUseCase(),
            userEventFlow
        ).distinctUntilChanged()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                initialValue = SummonerState.Loading,
                started = SharingStarted.WhileSubscribed(5_000)
            )

    override val uiState: StateFlow<UiState>

    init {
        val initialState = UiState()
        uiState = eventFlow
            .onStart { emit(Event.OnInit) }
            .sendEvent {
                when (it) {
                    is Event.OnBookmark -> saveBookmarkIdUseCase(it.summonerId)
                    is Event.OnDeleteAll -> deleteAllSummonerUseCase()
                    is Event.OnDelete -> deleteSummonerUseCase(it.name)
                    else -> return@sendEvent
                }
            }
            .eventFilter()
            .onEach { userEvent.emit(it) }
            .combine(summonerListState) { event, summonerState ->
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
        it is Event.OnInit || it is Event.OnRefresh || it is Event.OnNextPage || it is Event.OnSortedBookmark
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