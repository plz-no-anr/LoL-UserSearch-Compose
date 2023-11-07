package com.plznoanr.lol.feature.summoner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.summoner.GetSummonerUseCase
import com.plznoanr.lol.feature.summoner.navigation.SummonerArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import plznoanr.coma.core.ComaViewModel
import javax.inject.Inject

@HiltViewModel
class SummonerViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val getSummonerUseCase: GetSummonerUseCase
) : ComaViewModel<SummonerUiState, SummonerIntent, SummonerSideEffect>() {

    private val summonerName: String = SummonerArgs(stateHandle).summonerName

    override fun setInitialState(): SummonerUiState = SummonerUiState()

    override fun handleIntents(intent: SummonerIntent) {
        when (intent) {
            is SummonerIntent.OnLoad -> requestSummonerData()
            is SummonerIntent.Navigation.Back -> postSideEffect { SummonerSideEffect.Navigation.Back }
            is SummonerIntent.Spectator.OnWatch -> postSideEffect {
                SummonerSideEffect.Navigation.ToSpectator(
                    intent.summonerId
                )
            }
        }
    }

    private fun requestSummonerData() {
        getSummonerUseCase(summonerName.trim())
            .onStart { reduce { copy(isLoading = true) } }
            .onEach { result ->
                result.onSuccess {
                    reduce { copy(data = it, isLoading = false) }
                }.onFailure {
                    reduce { copy(error = it.message, isLoading = false) }
                }
            }.launchIn(viewModelScope)
    }

}