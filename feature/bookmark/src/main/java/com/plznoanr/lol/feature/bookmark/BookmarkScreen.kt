package com.plznoanr.lol.feature.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.mvibase.rememberEvent
import com.plznoanr.lol.core.ui.EmptyBox

@Composable
fun BookmarkRoute(
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent = rememberEvent(viewModel::onEvent)

    BookmarkScreen(
        uiState = uiState,
        onEvent = onEvent
    )

}

@Composable
internal fun BookmarkScreen(
    uiState: UiState,
    onEvent: (Event) -> Unit,
) {
    if (uiState.bookmarkList.isNotEmpty()) {
        BookmarkContent(
            bookmarkList = uiState.bookmarkList,
            onBookmark = { onEvent(Event.OnBookmark(it)) },
            onClear = { onEvent(Event.OnClear) }
        )
    } else {
        EmptyBox()
    }

}