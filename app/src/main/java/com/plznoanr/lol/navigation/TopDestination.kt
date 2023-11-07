package com.plznoanr.lol.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.plznoanr.lol.core.designsystem.icon.AppIcons
import com.plznoanr.lol.feature.bookmark.R as BookmarkR
import com.plznoanr.lol.feature.home.R as HomeR
import com.plznoanr.lol.feature.search.R as SearchR
import com.plznoanr.lol.feature.setting.R as SettingR

enum class TopDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTitleId: Int,
) {
    Home(
        selectedIcon = AppIcons.Home,
        unselectedIcon = AppIcons.HomeBorder,
        iconTitleId = HomeR.string.home_title,
    ),
   Search(
        selectedIcon = AppIcons.Search,
        unselectedIcon = AppIcons.SearchBorder,
        iconTitleId = SearchR.string.search_title,
    ),
    Bookmark(
        selectedIcon = AppIcons.BookMark,
        unselectedIcon = AppIcons.BookMarkBorder,
        iconTitleId = BookmarkR.string.bookmark_title,
    ),
    Setting(
        selectedIcon = AppIcons.Setting,
        unselectedIcon = AppIcons.SettingBorder,
        iconTitleId = SettingR.string.setting_title,
    )

}

