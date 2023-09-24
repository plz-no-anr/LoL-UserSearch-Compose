package com.plz.no.anr.lol.ui.navigation

import androidx.navigation.NavController


fun NavController.navigateToMain() {
    navigate(route = Destination.Main.route)
}

fun NavController.navigateToSearch() {
    navigate(route = Destination.Search.route)
}

fun NavController.navigateToSpectator(id: String) {
    navigate(route = Destination.Spectator.routeWithArgs(id)) {
        popUpTo(Destination.Main.route) {
            inclusive = false
        }
    }
}

fun NavController.navigateToSummoner(name: String) {
    navigate(route = Destination.Summoner.routeWithArgs(name))
}