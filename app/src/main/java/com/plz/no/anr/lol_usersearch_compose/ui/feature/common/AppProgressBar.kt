package com.plz.no.anr.lol_usersearch_compose.ui.feature.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.plznoanr.lol_usersearch_compose.R

@Composable
fun AppProgressBar(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.progress))

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            modifier = modifier
                .size(70.dp)
                .clip(CircleShape),
            iterations = LottieConstants.IterateForever,
            speed = 1.2f
        )
    }
}




@Preview
@Composable
fun AppProgressBarPreview() {
    AppProgressBar()
}