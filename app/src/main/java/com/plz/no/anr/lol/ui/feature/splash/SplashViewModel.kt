package com.plz.no.anr.lol.ui.feature.splash

import androidx.lifecycle.ViewModel
import com.plz.no.anr.lol.domain.usecase.json.InitialLocalJsonUseCase
import com.plz.no.anr.lol.utils.NetworkManager
import com.plz.no.anr.lol.utils.NetworkState
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