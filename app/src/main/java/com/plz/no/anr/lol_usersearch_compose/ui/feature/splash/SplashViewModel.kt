package com.plz.no.anr.lol_usersearch_compose.ui.feature.splash

import androidx.lifecycle.ViewModel
import com.plz.no.anr.lol_usersearch_compose.utils.NetworkManager
import com.plz.no.anr.lol_usersearch_compose.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val networkManager: NetworkManager,
) : ViewModel() {

    fun isNetworkAvailable(): Boolean {
        return networkManager.networkState.value is NetworkState.Connected
    }
}