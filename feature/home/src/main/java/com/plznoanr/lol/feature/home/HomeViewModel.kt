package com.plznoanr.lol.feature.home

import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.summoner.DeleteAllSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.DeleteSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.GetSummonerListUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.SaveBookmarkIdUseCase
import com.plznoanr.lol.core.mvibase.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSummonerListUseCase: GetSummonerListUseCase,
    private val deleteAllSummonerUseCase: DeleteAllSummonerUseCase,
    private val deleteSummonerUseCase: DeleteSummonerUseCase,
    private val saveBookmarkIdUseCase: SaveBookmarkIdUseCase
) : MviViewModel<UiState, Event, SideEffect>() {

    private val loadEvent = MutableSharedFlow<Unit>(extraBufferCapacity = Int.MAX_VALUE)

    private val summonerList = loadEvent.flatMapLatest {
        getSummonerListUseCase()
    }.map {
        it.toPersistentList()
    }.stateIn(
        scope = viewModelScope,
        initialValue = persistentListOf(),
        started = SharingStarted.WhileSubscribed(5_000)
    )

    override val uiState: StateFlow<UiState>

    init {
        val initialState = UiState()
        uiState = eventFlow.sendEvent {
            when (it) {
                is OnBookmark -> saveBookmarkIdUseCase(it.summonerId)
                is OnDeleteAll -> deleteAllSummonerUseCase()
                is OnDelete -> deleteSummonerUseCase(it.name)
                is OnInit, OnNextPage -> onLoad()
                else -> return@sendEvent
            }
        }.toStateChangeFlow(initialState) { state, event ->
            when (event) {
                is OnRefresh -> state.copy(isRefreshing = true)
                is OnInit -> state.copy(isLoading = true)
                is OnNextPage -> state.copy(loadNextPage = true)
                else -> state
            }
        }.combine(summonerList) { state, list ->
            state.copy(
                isRefreshing = false,
                isLoading = false,
                loadNextPage = false,
                summonerList = list
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = initialState,
            started = SharingStarted.Eagerly
        )
        viewModelScope.launch { onLoad() }
    }

    private suspend fun onLoad() {
        loadEvent.emit(Unit)
    }


}