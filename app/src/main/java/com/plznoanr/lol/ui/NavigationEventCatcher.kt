package com.plznoanr.lol.ui

import androidx.compose.runtime.Stable
import com.plznoanr.lol.navigation.TopDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@Stable
class NavigationEventCatcher {

    private val eventChannel: MutableSharedFlow<TopDestination> = MutableSharedFlow(Int.MAX_VALUE)
    val eventCallBack: Flow<TopDestination> = eventChannel.onEach { Timber.d("Click $it") }

    fun onNavigateEvent(topDestination: TopDestination) {
        eventChannel.tryEmit(topDestination)
    }

}