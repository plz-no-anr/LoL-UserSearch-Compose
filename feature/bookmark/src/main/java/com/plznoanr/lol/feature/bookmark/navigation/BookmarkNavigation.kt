package com.plznoanr.lol.feature.bookmark.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val BookmarkRoute = "bookmark_route"

fun NavController.navigateToBookmark(navOptions: NavOptions? = null) {
    navigate(BookmarkRoute, navOptions)
}