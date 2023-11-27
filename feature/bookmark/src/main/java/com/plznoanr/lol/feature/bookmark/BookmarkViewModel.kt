package com.plznoanr.lol.feature.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.summoner.GetBookmarkedSummonerListUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.SaveBookmarkIdUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class BookmarkViewModel @Inject constructor(
    getBookmarkedSummonerListUseCase: GetBookmarkedSummonerListUseCase,
    private val saveBookmarkIdUseCase: SaveBookmarkIdUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<BookmarkUiState> =
        MutableStateFlow(BookmarkUiState.Loading)

    val uiState: StateFlow<BookmarkUiState> =
        getBookmarkedSummonerListUseCase().map {
            if (it.isEmpty()) {
                BookmarkUiState.Error("북마크된 소환사가 없습니다.")
            } else {
                BookmarkUiState.Success(it.toPersistentList())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BookmarkUiState.Loading
        )



}