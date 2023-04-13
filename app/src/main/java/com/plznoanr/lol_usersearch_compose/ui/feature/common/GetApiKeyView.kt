package com.plznoanr.lol_usersearch_compose.ui.feature.common

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.plznoanr.lol_usersearch_compose.utils.Constants

@Composable
fun GetApiKeyView() {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(Constants.RIOT_DEV_ADDRESS),
    )
    LocalContext.current.startActivity(intent)
}