package com.plznoanr.lol.core.designsystem.component

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun GetApiKeyView() {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://developer.riotgames.com/"),
    )
    LocalContext.current.startActivity(intent)
}