package com.plznoanr.lol.core.ui.summoner

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Square
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plznoanr.lol.core.designsystem.R
import com.plznoanr.lol.core.designsystem.component.IconImage
import com.plznoanr.lol.core.designsystem.icon.AppIcons
import com.plznoanr.lol.core.designsystem.theme.LolUserSearchTheme
import com.plznoanr.lol.core.model.Nickname
import com.plznoanr.lol.core.model.toText

@Composable
fun SummonerItem(
    modifier: Modifier = Modifier,
    icon: String = "",
    nickname: String = "",
    level: String = "",
    tierRank: String = "",
    tierIcon: String = "",
    isBookmark: Boolean = false,
    isHideBookmark: Boolean = false,
    lpWinLose: String = "",
    progress: String? = null,
    onBookmarked: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SummonerView(
                icon = icon,
                nickname = nickname,
                level = level
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TierView(
                    modifier = modifier
                        .weight(1f),
                    tierRank = tierRank,
                    tierIcon = tierIcon(tierIcon),
                    isBookmark = isBookmark,
                    isHideBookmark = isHideBookmark,
                    onBookmarked = onBookmarked
                )

                Spacer(modifier = Modifier.height(20.dp))

                LeagueInfoView(
                    modifier = modifier
                        .weight(1f),
                    pointWinLose = lpWinLose,
                    progress = progress,
                )
            }

        }
    }

}

@Composable
private fun SummonerView(
    icon: String,
    nickname: String,
    level: String
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        IconImage(
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(10)),
            url = icon
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = nickname,
            modifier = Modifier.width(90.dp),
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Text(
            text = level,
            modifier = Modifier,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun TierView(
    modifier: Modifier = Modifier,
    tierRank: String,
    tierIcon: Painter,
    isBookmark: Boolean,
    isHideBookmark: Boolean,
    onBookmarked: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = tierIcon,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
        )

        Column {
            Text(
                text = stringResource(id = R.string.solo_rank),
                modifier = Modifier
                    .padding(bottom = 8.dp),
                fontSize = 12.sp
            )

            Text(
                text = tierRank,
                modifier = Modifier,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (!isHideBookmark) {
            IconButton(onClick = { onBookmarked() }) {
                Icon(
                    imageVector = if (isBookmark) AppIcons.BookMark else AppIcons.BookMarkBorder,
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

    }
}

@Composable
private fun LeagueInfoView(
    modifier: Modifier = Modifier,
    pointWinLose: String,
    progress: String?,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ) {

        LeaguePointView(
            pointWinLose = pointWinLose
        )

        progress?.let {
            MiniSeriesView(
                progress = it
            )
        }

    }
}

@Composable
private fun LeaguePointView(
    pointWinLose: String
) {
    Row {
        Text(
            text = pointWinLose,
            fontSize = 15.sp
        )
    }
}

//@Composable
//private fun IconView(
//    isPlaying: Boolean = false,
//    onAddClick: () -> Unit = {},
//    onDeleteClick: () -> Unit = {},
//    onSpectator: () -> Unit = {}
//) {
//    Row(
//        Modifier.padding(top = 8.dp)
//    ) {
//        Icon(
//            AppIcons.Add,
//            contentDescription = null,
//            modifier = Modifier
//                .clickable { onAddClick() }
//        )
//        Spacer(modifier = Modifier.width(4.dp))
//        Icon(
//            AppIcons.Delete,
//            contentDescription = null,
//            modifier = Modifier
//                .clickable { onDeleteClick() }
//        )
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        SpectatorView(
//            modifier = Modifier,
//            isPlaying = isPlaying,
//            onSpectator = onSpectator
//        )
//
//    }
//
//}

@Composable
private fun MiniSeriesView(
    progress: String
) {
    Row {
        if (progress != "No") {
            progress.forEach { result ->
                when (result) {
                    'W' -> Icon(
                        imageVector = AppIcons.Check,
                        contentDescription = null,
                        tint = Color.Green
                    )

                    'L' -> Icon(
                        imageVector = AppIcons.Close,
                        contentDescription = null,
                        tint = Color.Red
                    )

                    'N' -> Icon(
                        imageVector = AppIcons.HorizontalRule,
                        contentDescription = null
                    )
                }
            }
        }

    }
}

@Composable
private fun SpectatorView(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    onSpectator: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable {
                if (isPlaying) onSpectator()
            }
    ) {
        Text(
            text = stringResource(id = R.string.playing),
            fontSize = 12.sp
        )
        Icon(
            imageVector = Icons.Rounded.Square,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp),
            tint = if (isPlaying) Color.Green else Color.Red
        )
    }
}

@Preview
@Composable
private fun HomeItemPreview() {
    LolUserSearchTheme(darkTheme = true) {
        SummonerItem(
            icon = "http://ddragon.leagueoflegends.com/cdn/13.6.1/img/profileicon/6334.png",
            nickname = Nickname("hide on bush", "KR1").toText(),
            level = "Lv 33",
            tierRank = "CHALLENGER I",
            tierIcon = "CHALLENGER",
            isBookmark = false
        ) {}
    }

}