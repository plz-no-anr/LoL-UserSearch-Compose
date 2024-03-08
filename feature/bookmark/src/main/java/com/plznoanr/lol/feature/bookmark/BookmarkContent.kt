package com.plznoanr.lol.feature.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plznoanr.lol.core.designsystem.component.OnBottomReached
import com.plznoanr.lol.core.designsystem.component.summoner.SummonerItem
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.model.toText
import kotlinx.collections.immutable.PersistentList
import timber.log.Timber

@Composable
internal fun BookmarkContent(
    bookmarkList: PersistentList<Summoner>,
    onNextPage: () -> Unit,
    onBookmark: (String) -> Unit,
    onClear: () -> Unit
) {
    Column {
        val lazyColumnState = rememberLazyListState().apply {
            OnBottomReached {
                Timber.d("HomeScreen call")
                if (bookmarkList.size >= 20) {
                    onNextPage()
                }
            }
        }
        TextButton(modifier = Modifier.align(Alignment.End), onClick = onClear) {
            Text(text = "전체 해제", color = MaterialTheme.colorScheme.secondary)
        }
        LazyColumn(
            state = lazyColumnState,
        ) {
            items(bookmarkList) {
                SummonerItem(
                    icon = it.icon,
                    nickname = it.nickname.toText(),
                    level = it.levelInfo,
                    tierRank = it.tierRank,
                    tierIcon = it.tier,
                    isBookmark = it.isBookMarked,
                    lpWinLose = it.lpWinLose,
                    progress = it.miniSeries?.progress,
                    onBookmarked = { onBookmark(it.id) }
                )
            }
        }
    }
}