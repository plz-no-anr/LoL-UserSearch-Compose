package com.plznoanr.lol

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

sealed class MainState {
    data object Loading : MainState()

    data class Success(val data: Boolean) : MainState()

    data class Error(val throwable: Throwable) : MainState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    initialLocalJsonUseCase: InitialLocalJsonUseCase
) : ViewModel() {

    val mainState: StateFlow<MainState> = initialLocalJsonUseCase()
        .map {
            if (it.isSuccess) {
                MainState.Success(it.getOrThrow())
            } else {
                MainState.Error(it.exceptionOrNull()!!)
            }
        }.onEach {
            Timber.d("appState: $it")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainState.Loading
        )

}