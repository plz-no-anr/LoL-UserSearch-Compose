package com.plznoanr.lol.feature.summoner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.plznoanr.lol.core.domain.usecase.summoner.RequestSummonerUseCase
import com.plznoanr.lol.feature.summoner.navigation.SummonerArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SummonerViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val requestSummonerUseCase: RequestSummonerUseCase
): ViewModel() {

    private val summonerName: String = SummonerArgs(stateHandle).summonerName


}