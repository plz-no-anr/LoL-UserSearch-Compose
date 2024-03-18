package com.plznoanr.lol.feature.bookmark

import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.summoner.ClearBookmarkUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.GetBookmarkedSummonerListUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.SaveBookmarkIdUseCase
import com.plznoanr.lol.core.mvibase.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    getBookmarkedSummonerListUseCase: GetBookmarkedSummonerListUseCase,
    private val saveBookmarkIdUseCase: SaveBookmarkIdUseCase,
    private val clearBookmarkUseCase: ClearBookmarkUseCase
) : MviViewModel<UiState, Event, SideEffect>() {

    private val bookmarkListState = getBookmarkedSummonerListUseCase()
        .map {
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
                is Event.OnBookmark -> saveBookmarkIdUseCase(it.id)
                is Event.OnClear -> clearBookmarkUseCase()
            }
        }.reduce(initialState) { state, _ ->
            state
        }.combine(bookmarkListState) { state, list ->
            state.copy(
                bookmarkList = list
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = initialState,
            started = SharingStarted.Eagerly
        )

    }

}