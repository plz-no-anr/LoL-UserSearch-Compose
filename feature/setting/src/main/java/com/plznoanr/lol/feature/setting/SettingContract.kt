package com.plznoanr.lol.feature.setting

import androidx.compose.runtime.Stable
import com.plznoanr.lol.core.mvibase.MviEvent
import com.plznoanr.lol.core.mvibase.MviSideEffect
import com.plznoanr.lol.core.mvibase.MviState

@Stable
data class UiState(
    val isLoading: Boolean
): MviState

sealed interface Event: MviEvent

sealed interface SideEffect: MviSideEffect