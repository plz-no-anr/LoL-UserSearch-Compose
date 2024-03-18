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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.shareIn
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
        combine(getKeyUseCase(), getDarkThemeUseCase()) { key, isDark ->
            key to isDark
        }.stateIn(
            scope = viewModelScope,
            initialValue = "" to false,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    private val debounceFlow = eventFlow
        .debounce(400L)
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily
        )

    override val uiState: StateFlow<UiState>

    init {
        val initialState = UiState()
        uiState = debounceFlow
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
                }
            }.reduce(initialState) { state, _ ->
                state
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