package com.plznoanr.lol.feature.search

import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.search.DeleteAllSearchUseCase
import com.plznoanr.lol.core.domain.usecase.search.DeleteSearchUseCase
import com.plznoanr.lol.core.domain.usecase.search.GetRecentSearchListUseCase
import com.plznoanr.lol.core.domain.usecase.search.SaveSearchUseCase
import com.plznoanr.lol.core.model.Nickname
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
class SearchViewModel @Inject constructor(
    getRecentSearchListUseCase: GetRecentSearchListUseCase,
    private val deleteSearchUseCase: DeleteSearchUseCase,
    private val deleteSearchAllUseCase: DeleteAllSearchUseCase,
    private val insertSearchUseCase: SaveSearchUseCase
) : MviViewModel<UiState, Event, SideEffect>() {

    private val recentSearchList = getRecentSearchListUseCase()
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
                is Event.OnDelete -> {
                    val nickname = it.name.toNickName()
                    nickname?.also {
                        deleteSearchUseCase(it.name)
                    }
                }
                is Event.OnDeleteAll -> deleteSearchAllUseCase()
                is Event.OnSearch -> {
                    val nickname = it.fullName.toNickName()
                    if (nickname != null) {
                        postEffect(NavigateToSummoner(nickname.name, nickname.tag))
                        insertSearchUseCase(nickname)
                    } else {
                        postEffect(ShowSnackbar("소환사명을 확인해 주세요"))
                    }
                }
                else -> return@sendEvent
            }
        }.reduce(initialState) { state, event ->
            when (event) {
                is Event.OnActiveChange ->
                    state.copy(
                        isActive = event.isActive
                    )

                is Event.OnQueryChange -> state.copy(
                    query = event.query
                )

                else -> state
            }
        }.combine(recentSearchList) { state, recentList ->
            state.copy(
                data = recentList
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = initialState,
            started = SharingStarted.Eagerly
        )
    }

    private fun String.toNickName() = this.split("#").let {
        if (it.isNotEmpty() && it.size == 2) Nickname(it[0], it[1]) else null
    }

}