package com.plz.no.anr.lol.ui.feature.common

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.plz.no.anr.lol.utils.Constants

@Composable
fun GetApiKeyView() {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(Constants.RIOT_DEV_ADDRESS),
    )
    LocalContext.current.startActivity(intent)
}