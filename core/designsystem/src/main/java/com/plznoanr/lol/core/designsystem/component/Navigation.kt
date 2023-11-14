package com.plznoanr.lol.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plznoanr.lol.core.designsystem.theme.navigationIndicator
import com.plznoanr.lol.core.designsystem.theme.navigationSelect
import com.plznoanr.lol.core.designsystem.theme.navigationUnSelect

@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.primaryContainer,
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
fun navigationSelectedColor() = MaterialTheme.colorScheme.navigationSelect

@Composable
fun navigationUnselectedColor() = MaterialTheme.colorScheme.navigationUnSelect

@Composable
fun navigationIndicatorColor() = MaterialTheme.colorScheme.navigationIndicator