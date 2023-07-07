package com.plz.no.anr.lol.ui.feature.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun IconImage(
    modifier: Modifier = Modifier,
    url: String,
    scale: ContentScale = ContentScale.FillWidth
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = "Summoner Icon",
        modifier = modifier,
        contentScale = scale
    )

}