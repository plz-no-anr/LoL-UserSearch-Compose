package com.plznoanr.lol.feature.summoner

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.rounded.Square
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plznoanr.lol.core.designsystem.component.IconImage
import com.plznoanr.lol.core.designsystem.component.summoner.tierIcon
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.model.getDummySummoner
import com.plznoanr.lol.core.model.toText

@Composable
fun SummonerContent(
    modifier: Modifier = Modifier,
    summoner: Summoner,
    onWatch: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),

        ) {
        SummonerInfoView(
            icon = summoner.icon,
            nickname = summoner.nickname.toText(),
            level = summoner.levelInfo
        )

        Spacer(modifier = Modifier.height(20.dp))

        LeagueInfoView(
            pointWinLose = summoner.lpWinLose,
        )
        Spacer(modifier = Modifier.height(20.dp))

        TierRankView(
            tierRank = summoner.tierRank,
            tierIcon = tierIcon(tier = summoner.tier),
            miniSeries = summoner.miniSeries,
            isPlaying = false, // todo
            onSpectator = { onWatch(summoner.id) }
        )

    }

}

@Composable
private fun SummonerInfoView(
    modifier: Modifier = Modifier,
    icon: String,
    nickname: String,
    level: String,
) {
    Row(
        modifier = modifier
    ) {
        IconImage(
            modifier = Modifier
                .size(130.dp),
            url = icon
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = nickname,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = level,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
private fun TierRankView(
    modifier: Modifier = Modifier,
    tierRank: String,
    tierIcon: Painter,
    miniSeries: Summoner.MiniSeries? = null,
    isPlaying: Boolean = false,
    onSpectator: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .size(120.dp),
            painter = tierIcon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = tierRank,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))

            miniSeries?.also {
                SummonerMiniView(
                    miniSeries = it,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            SpectatorView(
                isPlaying = isPlaying,
                onSpectator = onSpectator
            )
        }


    }
}

@Composable
private fun LeagueInfoView(
    modifier: Modifier = Modifier,
    pointWinLose: String,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = pointWinLose,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

    }

}

@Composable
private fun SummonerMiniView(
    miniSeries: Summoner.MiniSeries,
) {
    Row {
        miniSeries.progress.let {
            if (it != "No") {
                it.forEach { result ->
                    when (result) {
                        'W' -> Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
                        'L' -> Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.Red
                        )
                        'N' -> Icon(
                            imageVector = Icons.Default.HorizontalRule,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun SpectatorView(
    isPlaying: Boolean = false,
    onSpectator: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable { onSpectator() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.playing),
            fontSize = 20.sp,
            modifier = Modifier,
            fontWeight = FontWeight.Bold
        )
        Icon(
            imageVector = Icons.Rounded.Square,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.Bottom),
            tint = if (isPlaying) Color.Green else Color.Red
        )
    }
}

@Preview
@Composable
private fun SummonerViewPreview() {
    SummonerContent(summoner = getDummySummoner()) {}
}