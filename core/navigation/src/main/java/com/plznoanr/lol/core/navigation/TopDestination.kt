package com.plznoanr.lol.core.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.plznoanr.lol.core.designsystem.icon.AppIcons

@Immutable
sealed class TopDestination(
    val route: NavGraph,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTitleId: Int,
) {
    data object Home: TopDestination(
        route = NavGraph.HomeRoute,
        selectedIcon = AppIcons.Home,
        unselectedIcon = AppIcons.HomeBorder,
        iconTitleId = R.string.home,
    )
   data object Search: TopDestination(
       route = NavGraph.SearchGraph.SearchRoute,
       selectedIcon = AppIcons.Search,
       unselectedIcon = AppIcons.SearchBorder,
       iconTitleId = R.string.search,
   )
    data object Bookmark: TopDestination(
        route = NavGraph.BookmarkRoute,
        selectedIcon = AppIcons.BookMark,
        unselectedIcon = AppIcons.BookMarkBorder,
        iconTitleId = R.string.bookmark,
    )
    data object Setting: TopDestination(
        route = NavGraph.SettingRoute,
        selectedIcon = AppIcons.Setting,
        unselectedIcon = AppIcons.SettingBorder,
        iconTitleId = R.string.setting,
    )
}

