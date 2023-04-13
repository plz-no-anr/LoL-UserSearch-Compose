package com.plznoanr.lol_usersearch_compose.ui.feature.splash

import androidx.lifecycle.ViewModel
import com.plznoanr.domain.usecase.json.InitialLocalJsonUseCase
import com.plznoanr.lol_usersearch_compose.utils.NetworkManager
import com.plznoanr.lol_usersearch_compose.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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