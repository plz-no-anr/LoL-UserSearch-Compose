package com.plznoanr.lol.core.domain.usecase.setting

import com.plznoanr.lol.core.data.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDarkThemeUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
     operator fun invoke(): Flow<Boolean> = settingRepository.getIsDarkTheme()
}
