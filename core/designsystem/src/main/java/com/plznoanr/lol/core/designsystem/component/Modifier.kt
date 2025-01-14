package com.plznoanr.lol.core.designsystem.component

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import timber.log.Timber

@Composable
fun Modifier.addFocusCleaner(
    focusManager: FocusManager
) = this.pointerInput(Unit) {
    detectTapGestures(
        onTap = {
            focusManager.clearFocus()
        }
    )
}

fun Modifier.singleClickable(
    enabled: Boolean = true,
    role: Role? = null,
    onClick: () -> Unit
) = this.composed {
    singleClickable(
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(),
        role = role,
        onClick = onClick
    )
}

fun Modifier.singleClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    role: Role? = null,
    onClick: () -> Unit
) = this.composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    singleEventHandler { manager ->
        Modifier.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { manager.onEvent { onClick() } },
            role = role,
            interactionSource = interactionSource,
            indication = indication,
        )
    }
}

fun Modifier.topBorder(strokeWidth: Dp, color: Color) = this.composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width

            drawLine(
                color = color,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = width, y = 0f),
                strokeWidth = strokeWidthPx
            )
        }
    }
)


fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = this.composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)


private interface SingleEventManager {
    fun onEvent(event: () -> Unit)
}

@OptIn(FlowPreview::class)
@Composable
private fun <T> singleEventHandler(
    content: @Composable (SingleEventManager) -> T
): T {
    val debounceState = remember {
        MutableSharedFlow<() -> Unit>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    val result = content(
        object : SingleEventManager {
            override fun onEvent(event: () -> Unit) {
                debounceState.tryEmit(event)
            }
        }
    )

    LaunchedEffect(true) {
        debounceState
            .debounce(300L) // 0.3초 동안 입력이 없을 시 가장 최신 이벤트 실행
            .collect { onClick ->
                Timber.d("singleEventHandler: onClick")
                onClick.invoke()
            }
    }

    return result
}