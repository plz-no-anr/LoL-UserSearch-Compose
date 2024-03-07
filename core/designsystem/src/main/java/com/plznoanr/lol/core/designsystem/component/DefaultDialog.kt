package com.plznoanr.lol.core.designsystem.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DefaultDialog(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    BackHandler {
        onDismiss()
    }

    Surface(
        color = Color.Transparent
    ) {
        Dialog(
            onDismissRequest = onDismiss,
        ) {
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .height(280.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp
                        )
                    )
            ) {
                content()
            }
        }
    }
}