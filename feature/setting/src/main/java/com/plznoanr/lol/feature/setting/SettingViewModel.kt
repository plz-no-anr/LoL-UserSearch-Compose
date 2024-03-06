package com.plznoanr.lol.feature.setting

import com.plznoanr.lol.core.mvibase.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(

): MviViewModel<UiState, Event, SideEffect>() {

    override val uiState: StateFlow<UiState>
        get() = TODO("Not yet implemented")
}