package com.plznoanr.lol.feature.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.EmptyBox
import com.plznoanr.lol.core.model.Summoner
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.launch

@Composable
fun BookmarkRoute(
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    BookmarkScreen(
        bookmarkList = uiState.bookmarkList,
        onEvent = { coroutineScope.launch { viewModel.onEvent(it) } }
    )

}

@Composable
internal fun BookmarkScreen(
    bookmarkList: PersistentList<Summoner>,
    onEvent: (Event) -> Unit,
) {
    if (bookmarkList.isNotEmpty()) {
        BookmarkContent(
            bookmarkList = bookmarkList,
            onNextPage = { onEvent(OnNextPage) },
            onBookmark = { onEvent(OnBookmark(it)) },
            onClear = { onEvent(OnClear) }
        )
    } else {
        EmptyBox()
    }

}