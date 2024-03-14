package com.plznoanr.lol.feature.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.EmptyBox
import com.plznoanr.lol.core.model.Summoner
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

@Composable
fun BookmarkRoute(
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val eventChannel = remember { Channel<Event>(Channel.UNLIMITED) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main.immediate) {
            eventChannel
                .consumeAsFlow()
                .onEach(viewModel::onEvent)
                .collect()
        }
    }
    val onEvent = remember {
        { event: Event ->
            eventChannel.trySend(event).getOrThrow()
        }
    }

    BookmarkScreen(
        bookmarkList = uiState.bookmarkList,
        onEvent = onEvent
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
            onBookmark = { onEvent(OnBookmark(it)) },
            onClear = { onEvent(OnClear) }
        )
    } else {
        EmptyBox()
    }

}