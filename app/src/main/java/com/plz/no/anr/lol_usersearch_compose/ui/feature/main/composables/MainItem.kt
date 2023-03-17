package com.plz.no.anr.lol_usersearch_compose.ui.feature.main.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.plznoanr.domain.model.Summoner
import com.plznoanr.lol_usersearch_compose.R.drawable

@Composable
fun MainItem(
    modifier: Modifier = Modifier,
    summoner: Summoner
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {

            SummonerView(
                icon = summoner.icon,
                name = summoner.name,
                level = summoner.level
            )

            Column() {
                TierView(tierRank = summoner.getTierRank())

                LeagueInfoView(pointWinLose = summoner.getLeaguePoint())
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
            .padding(start = 16.dp, bottom = 20.dp)
    ) {
        AsyncImage(
            model = icon,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = name,
            modifier = Modifier,
            fontSize = 13.sp
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
    tierRank: String,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        Image(
            painter = painterResource(id = drawable.emblem_bronze),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
        )

        Column(
            Modifier.padding(16.dp)
        ) {
            Text(
                text = "솔로랭크",
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
    pointWinLose: String,
) {
    Column(
        modifier = Modifier
    ) {
        Text(text = pointWinLose)

        Icon(
            Icons.Default.Add,
            contentDescription = null
        )

    }
}


@Preview
@Composable
fun MainItemPreview() {
    MainItem(
        summoner = Summoner(
            name = "name",
            level = "100",
            icon = "",
            tier = "tier",
            rank = "rank",
            leaguePoints = 32,
            wins = 20,
            losses = 20,
            isPlaying = false
        )
    )
}