package com.plznoanr.lol.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.summoner.DeleteAllSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.DeleteSummonerUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.GetSummonerListUseCase
import com.plznoanr.lol.core.domain.usecase.summoner.GetSummonerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSummonerListUseCase: GetSummonerListUseCase,
    private val getSummonerUseCase: GetSummonerUseCase,
    private val deleteAllSummonerUseCase: DeleteAllSummonerUseCase,
    private val deleteSummonerUseCase: DeleteSummonerUseCase,
) : ViewModel() {

    private val _eventFlow: MutableSharedFlow<HomeIntent> = MutableSharedFlow()

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        _eventFlow
            .onEach {
                when (it) {
                    is HomeIntent.Summoner.OnDeleteAll -> deleteAllSummonerUseCase()
                    is HomeIntent.Summoner.OnDelete -> deleteSummonerUseCase(it.name)
                    else -> Unit
                }
            }
            .filter { it !is HomeIntent.Summoner }
            .onEach {
                Timber.d("eventFlow : $it")
            }.onEach {
                when (it) {
                    is HomeIntent.OnRefresh -> {
                        _uiState.update { state ->
                            state.copy(isRefreshing = true)
                        }
                    }
                    is HomeIntent.OnLoadData -> {
                        _uiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                    is HomeIntent.OnLoadMore -> {
//                        _uiState.update { state ->
//                            state.copy(isLoading = true)
//                        }
                    }
                    else -> Unit
                }
            }.flatMapLatest {
                getSummonerListUseCase(it is HomeIntent.OnRefresh)
            }.map { result ->
                Timber.d("result : $result")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        data = result.toPersistentList(),
                    )
                }
            }.launchIn(viewModelScope)
        postIntent(HomeIntent.OnLoadData)
    }

    fun postIntent(intent: HomeIntent) {
        viewModelScope.launch {
            _eventFlow.emit(intent)
        }
    }

    private fun test() {
        viewModelScope.launch {
                getSummonerUseCase("dopa").onEach {
                    Timber.d("test : $it")
                }.first()
                getSummonerUseCase("hide on bush").onEach {
                    Timber.d("test : $it")
                }.first()
        }

    }


}