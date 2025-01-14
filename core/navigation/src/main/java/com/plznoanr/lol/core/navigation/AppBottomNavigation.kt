package com.plznoanr.lol.core.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.plznoanr.lol.core.designsystem.component.AppNavigationBar
import com.plznoanr.lol.core.designsystem.component.AppNavigationBarItem

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    destinations: List<TopDestination>,
    currentTopDestination: TopDestination?,
    currentDestination: NavDestination?,
    onNavigateTo: (TopDestination) -> Unit,
    onNavigateCallBack: (TopDestination) -> Unit
) {
    AppNavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { topDestination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(topDestination)
            AppNavigationBarItem(
                selected = selected,
                onClick = {
                    if (topDestination != currentTopDestination) {
                        onNavigateTo(topDestination)
                    } else {
                        onNavigateCallBack(currentTopDestination)
                    }
                },
                icon = {
                    Icon(
                        imageVector = topDestination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = topDestination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(text = stringResource(id = topDestination.iconTitleId))
                }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopDestination) =
    this?.hierarchy?.any {
        it.hasRoute(destination.route::class)
    } ?: false