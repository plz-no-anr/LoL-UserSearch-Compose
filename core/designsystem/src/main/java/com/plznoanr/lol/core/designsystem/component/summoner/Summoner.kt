package com.plznoanr.lol.core.designsystem.component.summoner

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.plznoanr.lol.core.designsystem.R

@Composable
fun TierIcon(tier: String) = when (tier) {
    "IRON" -> painterResource(id = R.drawable.emblem_iron)
    "BRONZE" -> painterResource(id = R.drawable.emblem_bronze)
    "SILVER" -> painterResource(id = R.drawable.emblem_silver)
    "GOLD" -> painterResource(id = R.drawable.emblem_gold)
    "PLATINUM" -> painterResource(id = R.drawable.emblem_platinum)
    "DIAMOND" -> painterResource(id = R.drawable.emblem_diamond)
    "MASTER" -> painterResource(id = R.drawable.emblem_master)
    "GRANDMASTER" -> painterResource(id = R.drawable.emblem_grandmaster)
    "CHALLENGER" -> painterResource(id = R.drawable.emblem_challenger)
    else -> painterResource(id = R.drawable.emblem_iron)
}
