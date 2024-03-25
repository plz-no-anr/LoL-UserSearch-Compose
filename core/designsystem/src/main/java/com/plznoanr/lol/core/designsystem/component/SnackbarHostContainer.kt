package com.plznoanr.lol.core.designsystem.component

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarHostState =
    compositionLocalOf<SnackbarHostState> { error("SnackbarHostState Error") }

@Composable
fun SnackbarHostContainer(
    snackbarHostState: SnackbarHostState,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        value = LocalSnackbarHostState provides snackbarHostState,
        content = content
    )

}