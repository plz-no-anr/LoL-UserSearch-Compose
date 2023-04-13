package com.plznoanr.lol_usersearch_compose.ui.feature.summoner.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.model.getDummySummoner
import com.plznoanr.lol_usersearch_compose.R
import com.plznoanr.lol_usersearch_compose.ui.feature.common.IconImage
import com.plznoanr.lol_usersearch_compose.ui.feature.common.summoner.getTierIcon
import com.plznoanr.lol_usersearch_compose.ui.feature.summoner.SummonerContract

@Composable
fun SummonerContent(
    modifier: Modifier = Modifier,
    data: Summoner,
    onIntent: (SummonerContract.Intent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),

        ) {
        SummonerInfoView(
            icon = data.icon,
            name = data.name,
            level = data.getLevels()
        )

        Spacer(modifier = Modifier.height(20.dp))

        LeagueInfoView(
            pointWinLose = data.getLeaguePoint(),
        )
        Spacer(modifier = Modifier.height(20.dp))

        TierRankView(
            tierRank = data.getTierRank(),
            tierIcon = getTierIcon(tier = data.tier),
            miniSeries = data.miniSeries,
            isPlaying = data.isPlaying,
            onSpectator = { onIntent(SummonerContract.Intent.Spectator.OnWatch(data.name)) }
        )

    }

}

@Composable
private fun SummonerInfoView(
    modifier: Modifier = Modifier,
    icon: String,
    name: String,
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
                text = name,
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
    tierIcon: Painter = painterResource(id = R.drawable.emblem_bronze),
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
    SummonerContent(data = getDummySummoner()) {}
}