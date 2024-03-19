package com.plznoanr.lol.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.plznoanr.lol.core.designsystem.icon.AppIcons
import com.plznoanr.lol.feature.bookmark.navigation.BookmarkRoute
import com.plznoanr.lol.feature.home.navigation.HomeRoute
import com.plznoanr.lol.feature.search.navigation.SearchRoute
import com.plznoanr.lol.feature.setting.navigation.SettingRoute
import com.plznoanr.lol.feature.bookmark.R as BookmarkR
import com.plznoanr.lol.feature.home.R as HomeR
import com.plznoanr.lol.feature.search.R as SearchR
import com.plznoanr.lol.feature.setting.R as SettingR

sealed class TopDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTitleId: Int,
) {
    data object Home: TopDestination(
        route = HomeRoute,
        selectedIcon = AppIcons.Home,
        unselectedIcon = AppIcons.HomeBorder,
        iconTitleId = HomeR.string.home_title,
    )
   data object Search: TopDestination(
       route = SearchRoute,
       selectedIcon = AppIcons.Search,
       unselectedIcon = AppIcons.SearchBorder,
       iconTitleId = SearchR.string.search_title,
   )
    data object Bookmark: TopDestination(
        route = BookmarkRoute,
        selectedIcon = AppIcons.BookMark,
        unselectedIcon = AppIcons.BookMarkBorder,
        iconTitleId = BookmarkR.string.bookmark_title,
    )
    data object Setting: TopDestination(
        route = SettingRoute,
        selectedIcon = AppIcons.Setting,
        unselectedIcon = AppIcons.SettingBorder,
        iconTitleId = SettingR.string.setting_title,
    )

}

