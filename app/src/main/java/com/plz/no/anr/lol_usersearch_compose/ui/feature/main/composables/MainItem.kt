package com.plz.no.anr.lol_usersearch_compose.ui.feature.main.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Square
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.IconImage
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.summoner.getTierIcon
import com.plz.no.anr.lol_usersearch_compose.ui.feature.main.MainContract
import com.plz.no.anr.lol_usersearch_compose.ui.theme.sky
import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.model.getDummySummoner
import com.plznoanr.lol_usersearch_compose.R

@Composable
fun MainItem(
    modifier: Modifier = Modifier,
    summoner: Summoner,
    onIntent: (MainContract.Intent) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = sky,
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {

            SummonerView(
                icon = summoner.icon,
                name = summoner.name,
                level = summoner.getLevels()
            )

            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .padding(vertical = 10.dp),
                color = Color.White
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TierView(
                    modifier = modifier
                        .weight(1f),
                    tierRank = summoner.getTierRank(),
                    tierIcon = getTierIcon(summoner.tier)
                )

                Spacer(modifier = Modifier.height(20.dp))

                LeagueInfoView(
                    modifier = modifier
                        .weight(1f),
                    pointWinLose = summoner.getLeaguePoint(),
                    miniSeries = summoner.miniSeries,
                    isPlaying = summoner.isPlaying,
                    onAdd = { onIntent(MainContract.Intent.Profile.OnAdd(summoner.asProfile())) },
                    onDelete = { onIntent(MainContract.Intent.Summoner.OnDelete(summoner.name)) },
                    onSpectator = { onIntent(MainContract.Intent.Spectator.OnWatch(summoner.name)) }
                )
            }

        }
    }

}

@Composable
private fun SummonerView(
    icon: String,
    name: String,
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
            text = name,
            modifier = Modifier,
            fontSize = 14.sp
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
    tierIcon: Painter = painterResource(id = R.drawable.emblem_bronze)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                top = 16.dp
            )
    ) {
        Image(
            painter = tierIcon,
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
        )

        Column {
            Text(
                text = stringResource(id = R.string.solo_rank),
                modifier = Modifier
                    .padding(bottom = 8.dp),
                fontSize = 13.sp
            )

            Text(
                text = tierRank,
                modifier = Modifier,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun LeagueInfoView(
    modifier: Modifier = Modifier,
    pointWinLose: String,
    miniSeries: Summoner.MiniSeries?,
    isPlaying: Boolean,
    onAdd: () -> Unit,
    onDelete: () -> Unit,
    onSpectator: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {

        LeaguePointView(
            pointWinLose = pointWinLose
        )

        miniSeries?.let {
            MiniSeriesView(
                miniSeries = it
            )
        }

        IconView(
            isPlaying = isPlaying,
            onAddClick = onAdd,
            onDeleteClick = onDelete,
            onSpectator = onSpectator
        )

    }
}

@Composable
private fun LeaguePointView(
    pointWinLose: String
) {
    Row {
        Text(
            text = pointWinLose,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun IconView(
    isPlaying: Boolean = false,
    onAddClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onSpectator: () -> Unit = {}
) {
    Row(
        Modifier.padding(top = 8.dp)
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier
                .clickable { onAddClick() }
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            Icons.Default.Delete,
            contentDescription = null,
            modifier = Modifier
                .clickable { onDeleteClick() }
        )

        Spacer(modifier = Modifier.weight(1f))

        SpectatorView(
            modifier = Modifier,
            isPlaying = isPlaying,
            onSpectator = onSpectator
        )

    }

}

@Composable
private fun MiniSeriesView(
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
private fun MainItemPreview() {
    MainItem(
        summoner = getDummySummoner()
    ) {}
}