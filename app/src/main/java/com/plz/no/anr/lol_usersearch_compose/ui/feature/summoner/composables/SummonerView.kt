package com.plz.no.anr.lol_usersearch_compose.ui.feature.summoner.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plz.no.anr.lol_usersearch_compose.ui.feature.common.IconImage
import com.plznoanr.domain.model.Summoner

@Composable
fun SummonerView(
    modifier: Modifier = Modifier,
    data: Summoner
) {
    Column(
        modifier = modifier
    ) {
        Text(text = data.name)
        Text(text = data.level)
    }

}

@Composable
fun SummonerInfoView(
    modifier: Modifier = Modifier,
    icon: String,
    name: String,
    level: String,
) {
    Column(
        modifier = modifier
    ) {
        IconImage(url = icon)
        Text(text = name)
        Text(text = level)
    }

}