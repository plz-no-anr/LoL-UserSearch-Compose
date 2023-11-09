package com.plznoanr.lol.core.domain.usecase.setting

import com.plznoanr.lol.core.data.repository.SettingRepository
import javax.inject.Inject

class SaveDarkThemeUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {

    suspend operator fun invoke(isDarkTheme: Boolean) {
        settingRepository.updateIsDarkTheme(isDarkTheme)
    }

}