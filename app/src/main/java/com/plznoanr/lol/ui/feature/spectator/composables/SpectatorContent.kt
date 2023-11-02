package com.plznoanr.lol.ui.feature.spectator.composables

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.plznoanr.lol.core.model.Spectator

@Composable
fun SpectatorContent(
    modifier: Modifier = Modifier,
    data: Spectator
) {
    val context = LocalContext.current
    var bitmapState by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(key1 = Unit) {
        bitmapState = BitmapFactory.decodeStream(context.assets.open("perk-images/Styles/Sorcery/PhaseRush/PhaseRush.png"))
    }


}


@Preview
@Composable
private fun SpectatorPreview() {
//    SpectatorView()
}