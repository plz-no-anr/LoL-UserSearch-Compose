package com.plznoanr.lol.feature.setting

import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.setting.GetDarkThemeUseCase
import com.plznoanr.lol.core.domain.usecase.setting.GetKeyUseCase
import com.plznoanr.lol.core.domain.usecase.setting.SaveDarkThemeUseCase
import com.plznoanr.lol.core.domain.usecase.setting.SaveKeyUseCase
import com.plznoanr.lol.core.mvibase.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    getKeyUseCase: GetKeyUseCase,
    getDarkThemeUseCase: GetDarkThemeUseCase,
    private val saveKeyUseCase: SaveKeyUseCase,
    private val saveDarkThemeUseCase: SaveDarkThemeUseCase
) : MviViewModel<UiState, Event, SideEffect>() {

    private val settingFlow =
        combine(getKeyUseCase(), getDarkThemeUseCase()) { key, darkMode ->
            key to darkMode
        }.stateIn(
            scope = viewModelScope,
            initialValue = "" to false,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    override val uiState: StateFlow<UiState>

    init {
        val initialState = UiState()
        uiState = eventFlow
            .sendEvent {
                when (it) {
                    is Event.OnThemeChange -> {
                        saveDarkThemeUseCase(it.isDarkTheme)
                        postEffect(
                            OnShowSnackbar(
                                if (it.isDarkTheme)
                                    "다크모드가 설정되었습니다."
                                else
                                    "다크모드가 해제되었습니다."
                            )
                        )
                    }
                    is Event.OnKeyChange -> {
                        saveKeyUseCase(it.key)
                        postEffect(OnShowSnackbar("키가 저장되었습니다!"))
                    }
                    else -> Unit
                }
            }.reduce(initialState) { state, event ->
                 when (event) {
                    is Event.OnQueryChange -> state.copy(
                        keyQuery = event.query
                    )
                    is Event.OnShowDialog -> state.copy(
                        showKeyChangeDialog = event.show
                    )
                    else -> state
                }
            }.combine(settingFlow) { state, settingPair ->
                state.copy(
                    apiKey = settingPair.first,
                    isDarkTheme = settingPair.second
                )
            }.stateIn(
                scope = viewModelScope,
                initialValue = initialState,
                started = SharingStarted.Eagerly
            )
    }
}