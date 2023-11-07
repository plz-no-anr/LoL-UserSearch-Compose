package com.plznoanr.lol.feature.bookmark.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.plznoanr.lol.feature.bookmark.BookmarkRoute

const val BookmarkRoute = "bookmark_route"

fun NavController.navigateToBookmark(navOptions: NavOptions? = null) {
    navigate(BookmarkRoute, navOptions)
}

fun NavGraphBuilder.bookmarkScreen(

) {
    composable(route = BookmarkRoute) {
        BookmarkRoute(

        )
    }
}