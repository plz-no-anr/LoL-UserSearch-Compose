package com.plznoanr.lol.core.mvibase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun <T> rememberEvent(onEvent: suspend (T) -> Unit): (T) -> Unit {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    return remember {
        { intent ->
            lifecycleScope.launch {
                withContext(Dispatchers.Main.immediate) {
                    onEvent(intent)
                }
            }
        }
    }
}