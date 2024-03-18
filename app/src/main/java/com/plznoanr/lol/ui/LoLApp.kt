package com.plznoanr.lol.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.R
import com.plznoanr.lol.navigation.AppBottomBar
import com.plznoanr.lol.navigation.AppNavHost
import com.plznoanr.lol.utils.NetworkManager

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AppNavHost(
                navController = appState.navController,
                onShowSnackbar = { message ->
                    snackbarHostState.showSnackbar(message = message, withDismissAction = true) == SnackbarResult.ActionPerformed
                },
                navigateCallbackFlow = navigationEventCatcher.eventCallBack
            )
        }

    }

}