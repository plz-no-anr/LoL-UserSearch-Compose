package com.plznoanr.lol.feature.bookmark

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.EmptyBox
import com.plznoanr.lol.core.designsystem.component.OnBottomReached
import com.plznoanr.lol.core.designsystem.component.summoner.SummonerItem
import com.plznoanr.lol.core.model.Summoner
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.launch
import timber.log.Timber

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
    val lazyColumnState = rememberLazyListState().apply {
        OnBottomReached {
            Timber.d("HomeScreen call")
            if (bookmarkList.size >= 20) {
                onEvent(OnNextPage)
            }
        }
    }

    if (bookmarkList.isNotEmpty()) {
        LazyColumn(
            state = lazyColumnState,
        ) {
            items(bookmarkList) {
                SummonerItem(
                    summoner = it,
                    onBookmarked = { onEvent(OnBookmark(it.id)) }
                )
            }
        }
    } else {
        EmptyBox()
    }

}