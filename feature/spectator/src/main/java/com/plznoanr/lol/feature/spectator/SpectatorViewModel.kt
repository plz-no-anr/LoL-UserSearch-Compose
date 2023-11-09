package com.plznoanr.lol.feature.spectator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.plznoanr.lol.core.domain.usecase.spectator.RequestSpectatorUseCase
import com.plznoanr.lol.feature.spectator.navigation.SpectatorArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpectatorViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val requestSpectatorUseCase: RequestSpectatorUseCase
): ViewModel() {

    private val summonerId: String = SpectatorArgs(stateHandle).summonerId


    private fun getSpectator() {
//        summonerId.let {
//            requestSpectatorUseCase(it.trim())
//                .onStart { reduce { copy(isLoading = true) } }
//                .onEach { result ->
//                    result.onSuccess {
//                        reduce { copy(data = it, isLoading = false) }
//                    }.onFailure {
//                        reduce { copy(error = it.message, isLoading = false) }
//                    }
//                }.launchIn(viewModelScope)
//        } ?: run {
//            reduce { copy(error = "Summoner name is null", isLoading = false) }
//        }
    }

}