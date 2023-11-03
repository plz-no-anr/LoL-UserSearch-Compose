package com.plznoanr.lol.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun AppNavHost(
    navController: NavController,
    onShowSnackbar: suspend  (String) -> Boolean,
) {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Destination.Main.route,
    ) {

    }

}
