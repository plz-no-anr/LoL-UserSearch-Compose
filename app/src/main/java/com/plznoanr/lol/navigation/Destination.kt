package com.plznoanr.lol.navigation


sealed class Destination(open val route: String) {

    data object Main : Destination("main")

    data object Search : Destination("search")

    data object Summoner : Destination("summoner") {
        object Args {
            const val KEY_SUMMONER_NAME = "summoner_name"
        }

        override val route: String
        get() = "${super.route}?{${Args.KEY_SUMMONER_NAME}}"

        fun routeWithArgs(summonerName: String) = "${super.route}?$summonerName"
    }

    data object Spectator : Destination("spectator") {
        object Args {
            const val KEY_SUMMONER_ID = "summoner_id"
        }

        override val route: String
        get() = "${super.route}?{${Args.KEY_SUMMONER_ID}}"

        fun routeWithArgs(summonerName: String) = "${super.route}?$summonerName"
    }

}

private fun generateArgsRoute(route: String, args: List<String>): String = route.let {
    args.fold(it) { r, arg ->
        r.plus("?{$arg}")
    }
}