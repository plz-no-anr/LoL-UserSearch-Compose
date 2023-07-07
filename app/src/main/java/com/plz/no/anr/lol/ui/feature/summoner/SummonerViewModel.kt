package com.plz.no.anr.lol.ui.feature.summoner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol.domain.usecase.summoner.RequestSummonerUseCase
import com.plz.no.anr.lol.ui.base.BaseViewModel
import com.plz.no.anr.lol.ui.feature.summoner.SummonerContract.Intent
import com.plz.no.anr.lol.ui.feature.summoner.SummonerContract.SideEffect
import com.plz.no.anr.lol.ui.feature.summoner.SummonerContract.State
import com.plz.no.anr.lol.ui.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummonerViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val requestSummonerUseCase: RequestSummonerUseCase
) : BaseViewModel<State, Intent, SideEffect>() {

    private val summonerName: String? by lazy {
        stateHandle.get<String>(Destination.Summoner.Args.KEY_SUMMONER_NAME)
    }

    override fun setInitialState(): State = State.initial()

    override fun handleIntents(intent: Intent) {
        when (intent) {
            is Intent.OnLoad -> requestSummonerData()
            is Intent.Navigation.Back -> postSideEffect { SideEffect.Navigation.Back }
            is Intent.Spectator.OnWatch -> postSideEffect { SideEffect.Navigation.ToSpectator(intent.name)}
        }
    }

    private fun requestSummonerData() {
        viewModelScope.launch {
            summonerName?.let {
                requestSummonerUseCase(it.trim())
                    .onStart { reduce { copy(isLoading = true) } }
                    .collect { result ->
                        result.onSuccess {
                            reduce { copy(data = it, isLoading = false) }
                        }.onFailure {
                            reduce { copy(error = it.message, isLoading = false) }
                        }
                    }
            } ?: run {
                reduce { copy(error = "Summoner name is null", isLoading = false) }
            }
        }
    }

}