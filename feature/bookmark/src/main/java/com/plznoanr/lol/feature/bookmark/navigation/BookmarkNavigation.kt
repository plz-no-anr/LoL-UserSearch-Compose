package com.plznoanr.lol.feature.bookmark.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.plznoanr.lol.feature.bookmark.BookmarkRoute

const val BookmarkRoute = "bookmark_route"

fun NavGraphBuilder.bookmarkScreen(

) {
    composable(route = BookmarkRoute) {
        BookmarkRoute(

        )
    }
}