package com.plznoanr.lol.feature.setting

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.plznoanr.lol.core.model.Profile
import com.plznoanr.lol.core.mvibase.MviEvent
import com.plznoanr.lol.core.mvibase.MviSideEffect
import com.plznoanr.lol.core.mvibase.MviUiState

@Stable
data class UiState(
    val profile: Profile? = null,
    val apiKey: String = "",
    val keyQuery: String = "",
    val isDarkTheme: Boolean = false,
    val showKeyChangeDialog: Boolean = false
): MviUiState

@Immutable
sealed interface Event: MviEvent {
    data class OnThemeChange(val isDarkTheme: Boolean): Event
    data class OnKeyChange(val key: String): Event
    data class OnQueryChange(val query: String): Event
    data class OnShowDialog(val show: Boolean): Event
}

sealed interface SideEffect: MviSideEffect

data class OnShowSnackbar(val message: String): SideEffect