package com.plz.no.anr.lol.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol.domain.usecase.json.InitialLocalJsonUseCase
import com.plz.no.anr.lol.utils.NetworkManager
import com.plz.no.anr.lol.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed class AppState {
    object Loading : AppState()

    data class Success(val data: Boolean) : AppState()

    data class Error(val throwable: Throwable) : AppState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val initialLocalJsonUseCase: InitialLocalJsonUseCase
) : ViewModel() {

    private val _appState: MutableStateFlow<AppState> = MutableStateFlow(AppState.Loading)

    init {
        Timber.w("$this created.")
        viewModelScope.launch {
            val init = initialLocalJsonUseCase(Unit)
                .onEach {
                    Timber.d("initialLocalJsonUseCase : $it")
                }.catch { _appState.emit(AppState.Error(it)) }
                .first()

            init.onSuccess {
                _appState.emit(AppState.Success(true))
            }.onFailure {
                _appState.emit(AppState.Error(it))
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        return networkManager.networkState.value is NetworkState.Connected
    }

    fun isAppInitialized(): Boolean = !(isNetworkAvailable() && _appState.value is AppState.Success)


}