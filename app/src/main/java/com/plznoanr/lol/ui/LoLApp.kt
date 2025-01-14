package com.plznoanr.lol.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.R
import com.plznoanr.lol.core.designsystem.component.SnackbarHostContainer
import com.plznoanr.lol.core.navigation.AppBottomBar
import com.plznoanr.lol.core.navigation.TopDestination
import com.plznoanr.lol.navigation.AppNavHost
import com.plznoanr.lol.utils.NetworkManager
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun LoLApp(
    networkManager: NetworkManager,
    appState: AppState = rememberAppState(
        networkManager = networkManager
    )
) {
    val isOnline by appState.isOnline.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val networkErrorMessage = stringResource(id = R.string.network_error)

    LaunchedEffect(isOnline) {
        if (!isOnline) {
            snackbarHostState.showSnackbar(
                message = networkErrorMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    val navigationEventCatcher = remember {
        NavigationEventCatcher()
    }
    val navCallback = remember {
        { topDestination: TopDestination ->
            navigationEventCatcher.eventCallBack.map { it == topDestination }.filter { it } }
    }

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                AppBottomBar(
                    destinations = appState.topDestinations,
                    currentTopDestination = appState.currentTopDestination,
                    currentDestination = appState.currentDestination,
                    onNavigateTo = appState::navigateTo,
                    onNavigateCallBack = navigationEventCatcher::onNavigateEvent
                )
            }
        }
    ) { paddingValues ->
       SnackbarHostContainer(snackbarHostState = snackbarHostState) {
           AppNavHost(
               modifier = Modifier.padding(paddingValues),
               navController = appState.navController,
               navigateCallbackFlow = navCallback
           )
       }
    }

}