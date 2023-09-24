package com.plz.no.anr.lol.ui.feature.summoner.composables

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plz.no.anr.lol.R
import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.domain.model.getDummySummoner
import com.plz.no.anr.lol.ui.feature.common.IconImage
import com.plz.no.anr.lol.ui.feature.common.summoner.getTierIcon
import com.plz.no.anr.lol.ui.feature.summoner.SummonerContract

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
            level = data.levelInfo
        )

        Spacer(modifier = Modifier.height(20.dp))

        LeagueInfoView(
            pointWinLose = data.lpWinLose,
        )
        Spacer(modifier = Modifier.height(20.dp))

        TierRankView(
            tierRank = data.tierRank,
            tierIcon = getTierIcon(tier = data.tier),
            miniSeries = data.miniSeries,
            onSpectator = { onIntent(SummonerContract.Intent.Spectator.OnWatch(data.id)) }
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
    }
}

@Preview
@Composable
private fun SummonerViewPreview() {
    SummonerContent(data = getDummySummoner()) {}
}