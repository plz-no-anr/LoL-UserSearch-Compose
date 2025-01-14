package com.plznoanr.lol.feature.bookmark.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.plznoanr.lol.core.navigation.NavGraph
import com.plznoanr.lol.feature.bookmark.BookmarkRoute

fun NavGraphBuilder.bookmarkScreen(
) {
    composable<NavGraph.BookmarkRoute> {
        BookmarkRoute(

        )
    }
}