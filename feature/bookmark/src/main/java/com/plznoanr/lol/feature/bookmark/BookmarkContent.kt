package com.plznoanr.lol.feature.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plznoanr.lol.core.designsystem.component.SummonerCardStack
import com.plznoanr.lol.core.designsystem.component.summoner.SummonerItem
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.model.toText
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun BookmarkContent(
    bookmarkList: ImmutableList<Summoner>,
    onBookmark: (String) -> Unit,
    onClear: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        TextButton(modifier = Modifier.align(Alignment.End), onClick = onClear) {
            Text(text = "전체 해제", color = MaterialTheme.colorScheme.secondary)
        }

        when (bookmarkList.size) {
            1 -> {
                val summoner = bookmarkList[0]
                SummonerItem(
                    icon = summoner.icon,
                    nickname = summoner.nickname.toText(),
                    level = summoner.levelInfo,
                    tierRank = summoner.tierRank,
                    tierIcon = summoner.tier,
                    isBookmark = summoner.isBookMarked,
                    lpWinLose = summoner.lpWinLose,
                    progress = summoner.miniSeries?.progress,
                    onBookmarked = { onBookmark(summoner.id) }
                )
            }
            else -> SummonerCardStack(
                summoners = bookmarkList,
                onBookmark = onBookmark
            )
        }

    }
}


@Composable
private fun Preview(

) {

}