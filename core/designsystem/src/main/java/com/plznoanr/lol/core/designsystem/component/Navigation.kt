package com.plznoanr.lol.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        tonalElevation = 0.dp,
        content = content,
    )
}


@Composable
fun RowScope.AppNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = navigationSelectedColor(),
            unselectedIconColor = navigationUnselectedColor(),
            selectedTextColor = navigationSelectedColor(),
            unselectedTextColor = navigationUnselectedColor(),
            indicatorColor = navigationIndicatorColor(),
        )
    )
}

@Composable
fun navigationSelectedColor() = MaterialTheme.colorScheme.onSurfaceVariant

@Composable
fun navigationUnselectedColor() = MaterialTheme.colorScheme.onSurfaceVariant

@Composable
fun navigationIndicatorColor() = MaterialTheme.colorScheme.primary