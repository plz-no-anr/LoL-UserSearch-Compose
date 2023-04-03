package com.plz.no.anr.lol_usersearch_compose.ui.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plz.no.anr.lol_usersearch_compose.utils.NetworkManager
import com.plz.no.anr.lol_usersearch_compose.utils.NetworkState
import com.plznoanr.domain.usecase.json.InitialLocalJsonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val initialLocalJsonUseCase: InitialLocalJsonUseCase
) : ViewModel() {

    suspend fun initLocalJson() {
        withContext(Dispatchers.IO) {
            initialLocalJsonUseCase(Unit)
                .collect {
                    it.onSuccess { result ->
                        if (result) {
                            Timber.d("Initial LocalJson success")
                        } else {
                            Timber.d("Already Init LocalJson")
                        }
                    }.onFailure { t ->
                        Timber.e(t)
                        Timber.d("Initial LocalJson fail")
                    }
                }
        }
    }

    fun isNetworkAvailable(): Boolean {
        return networkManager.networkState.value is NetworkState.Connected
    }
}