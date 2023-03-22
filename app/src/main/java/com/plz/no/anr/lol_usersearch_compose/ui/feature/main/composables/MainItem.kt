package com.plz.no.anr.lol_usersearch_compose.ui.feature.main.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.plz.no.anr.lol_usersearch_compose.ui.feature.main.MainContract
import com.plz.no.anr.lol_usersearch_compose.ui.theme.sky
import com.plznoanr.domain.model.Summoner
import com.plznoanr.lol_usersearch_compose.R

@Composable
fun MainItem(
    modifier: Modifier = Modifier,
    summoner: Summoner,
    onEvent: (MainContract.Event) -> Unit
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
                color = Color.DarkGray
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
                    onAdd = { onEvent(MainContract.Event.OnAddProfile(summoner.asProfile())) },
                    onDelete = { onEvent(MainContract.Event.OnDelete(summoner.name)) }
                )
            }

        }
    }

}

@Composable
fun SummonerView(
    icon: String,
    name: String,
    level: String
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(icon)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(10)),
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
fun TierView(
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
fun LeagueInfoView(
    modifier: Modifier = Modifier,
    pointWinLose: String,
    miniSeries: Summoner.MiniSeries?,
    isPlaying: Boolean,
    onAdd: () -> Unit,
    onDelete: () -> Unit
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
                miniSeries = it,
                isPlaying = isPlaying
            )
        }

        IconView(
            onAddClick = onAdd,
            onDeleteClick = onDelete
        )

    }
}

@Composable
fun LeaguePointView(
    pointWinLose: String
) {
    Row(
    ) {
        Text(
            text = pointWinLose,
            fontSize = 12.sp
        )


    }
}

@Composable
fun IconView(
    onAddClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
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

    }

}

@Composable
fun MiniSeriesView(
    miniSeries: Summoner.MiniSeries,
    isPlaying: Boolean = false,
    onSpectator: () -> Unit = {}
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
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .align(Alignment.Bottom)
                .clickable { onSpectator() }
        ) {
            Text(
                text = stringResource(id = R.string.playing),
                fontSize = 12.sp
            )
            Icon(
                imageVector = if (isPlaying) Icons.Rounded.Check else Icons.Rounded.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = if (isPlaying) Color.Green else Color.Red
            )
        }
    }
}
@Composable
private fun getTierIcon(tier: String) = when (tier) {
    "IRON" -> painterResource(id = R.drawable.emblem_iron)
    "BRONZE" -> painterResource(id = R.drawable.emblem_bronze)
    "SILVER" -> painterResource(id = R.drawable.emblem_silver)
    "GOLD" -> painterResource(id = R.drawable.emblem_gold)
    "PLATINUM" -> painterResource(id = R.drawable.emblem_platinum)
    "DIAMOND" -> painterResource(id = R.drawable.emblem_diamond)
    "MASTER" -> painterResource(id = R.drawable.emblem_master)
    "GRANDMASTER" -> painterResource(id = R.drawable.emblem_grandmaster)
    "CHALLENGER" -> painterResource(id = R.drawable.emblem_challenger)
    else -> painterResource(id = R.drawable.emblem_iron)
}

@Preview
@Composable
fun MainItemPreview() {
    MainItem(
        summoner = Summoner(
            name = "Hide On Bush",
            level = "100",
            icon = "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/6.png",
            tier = "GRANDMASTER",
            rank = "I",
            leaguePoints = 322,
            wins = 223,
            losses = 203,
            isPlaying = false,
            miniSeries = Summoner.MiniSeries(
                progress = "WLNNN",
                wins = 1,
                losses = 1,
                target = 3
            )
        )
    ) {}
}