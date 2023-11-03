package com.plznoanr.lol.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.plznoanr.lol.core.designsystem.icon.AppIcons
import com.plznoanr.lol.feature.home.R as HomeR
import com.plznoanr.lol.feature.search.R as SearchR
import com.plznoanr.lol.feature.bookmark.R as BookMarkR
import com.plznoanr.lol.feature.setting.R as SettingR

enum class TopDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTitleId: Int,
) {
    Home(
        selectedIcon = AppIcons.Home,
        unselectedIcon = AppIcons.HomeBorder,
        iconTitleId = HomeR.string.title,
    ),
   Search(
        selectedIcon = AppIcons.Search,
        unselectedIcon = AppIcons.SearchBorder,
        iconTitleId = SearchR.string.title,
    ),
    Bookmark(
        selectedIcon = AppIcons.BookMark,
        unselectedIcon = AppIcons.BookMarkBorder,
        iconTitleId = BookMarkR.string.title,
    ),
    Setting(
        selectedIcon = AppIcons.Setting,
        unselectedIcon = AppIcons.SettingBorder,
        iconTitleId = SettingR.string.title,
    )

}

