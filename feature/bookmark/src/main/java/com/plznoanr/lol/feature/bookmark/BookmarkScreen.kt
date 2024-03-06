package com.plznoanr.lol.feature.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        Column {
            TextButton(modifier = Modifier.align(Alignment.End), onClick = { onEvent(OnClear) }) {
                Text(text = "전체 해제", color = MaterialTheme.colorScheme.secondary)
            }
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
        }
    } else {
        EmptyBox()
    }

}