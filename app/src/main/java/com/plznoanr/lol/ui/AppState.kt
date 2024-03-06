package com.plznoanr.lol.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.plznoanr.lol.feature.bookmark.navigation.BookmarkRoute
import com.plznoanr.lol.feature.bookmark.navigation.navigateToBookmark
import com.plznoanr.lol.feature.home.navigation.HomeRoute
import com.plznoanr.lol.feature.home.navigation.navigateToHome
import com.plznoanr.lol.feature.search.navigation.SearchRoute
import com.plznoanr.lol.feature.search.navigation.navigateToSearch
import com.plznoanr.lol.feature.setting.navigation.SettingRoute
import com.plznoanr.lol.feature.setting.navigation.navigateToSetting
import com.plznoanr.lol.navigation.TopDestination
import com.plznoanr.lol.utils.NetworkManager
import com.plznoanr.lol.utils.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

@Composable
fun rememberAppState(
    networkManager: NetworkManager,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState {
    NavigationTrackingSideEffect(navController)
    return remember(
        networkManager,
        navController,
        coroutineScope
    ) {
        AppState(
            navController = navController,
            coroutineScope = coroutineScope,
            networkManager = networkManager,
        )
    }
}


@Stable
class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkManager: NetworkManager,
) {
    val isOnline: StateFlow<Boolean> = networkManager.networkState
        .onEach {
            Timber.d("networkState: $it")
        }
        .map {
            when (it) {
                NetworkState.Connected -> true
                else -> false
            }
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val topDestinations = TopDestination.entries.toList()

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        @Composable
        get() = currentTopDestination != null

    val currentTopDestination: TopDestination?
        @Composable get() = when (currentDestination?.route) {
            HomeRoute -> TopDestination.Home
            SearchRoute -> TopDestination.Search
            BookmarkRoute -> TopDestination.Bookmark
            SettingRoute -> TopDestination.Setting
            else -> null
        }

    fun navigateTo(destination: TopDestination) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (destination) {
            TopDestination.Home -> navController.navigateToHome(navOptions)
            TopDestination.Search -> navController.navigateToSearch(navOptions)
            TopDestination.Bookmark -> navController.navigateToBookmark(navOptions)
            TopDestination.Setting -> navController.navigateToSetting(navOptions)
        }
    }
}

@Composable
private fun NavigationTrackingSideEffect(navController: NavHostController) {
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { navController, destination, bundle ->
            Timber.d("[네비게이션] -> [$navController][$destination][$bundle]")
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}