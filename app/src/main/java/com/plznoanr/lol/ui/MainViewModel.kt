package com.plznoanr.lol.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.json.InitialLocalJsonUseCase
import com.plznoanr.lol.utils.NetworkManager
import com.plznoanr.lol.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

sealed class AppState {
    data object Loading : AppState()

    data class Success(val data: Boolean) : AppState()

    data class Error(val throwable: Throwable) : AppState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    networkManager: NetworkManager,
    initialLocalJsonUseCase: InitialLocalJsonUseCase
) : ViewModel() {

    private val networkState: StateFlow<NetworkState> = networkManager.networkState
        .onEach {
            Timber.d("networkState: $it")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = NetworkState.None
        )

    private val jsonInitialState: StateFlow<Boolean> = initialLocalJsonUseCase(Unit)
        .map {
            it.isSuccess
        }.onEach {
            Timber.d("jsonInitialState: $it")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val appState: StateFlow<AppState> =
        combine(
            networkState,
            jsonInitialState
        ) { state, jsonInit ->
            Timber.d("combine: $state, $jsonInit")
            when (state) {
                is NetworkState.None -> AppState.Loading
                is NetworkState.Connected -> AppState.Success(jsonInit)
                is NetworkState.NotConnected -> AppState.Error(Exception("네트워크 연결이 끊겼습니다."))
            }
        }.onEach {
            Timber.d("appState: $it")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AppState.Loading
        )

}