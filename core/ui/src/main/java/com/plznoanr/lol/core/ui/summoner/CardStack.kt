package com.plznoanr.lol.core.ui.summoner

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.model.toText
import kotlinx.collections.immutable.ImmutableList
import plznoanr.cardstack.CardStack
import plznoanr.cardstack.animation.VerticalAnimationStyle
import plznoanr.cardstack.ui.Orientation
import plznoanr.cardstack.ui.VerticalAlignment

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummonerCardStack(
    summoners: ImmutableList<Summoner>,
    onCardClick: (Int) -> Unit = {},
    onBookmark: (String) -> Unit = {}
) {
    CardStack(
        modifier = Modifier
            .background(Color.Transparent),
        cardCount = summoners.size,
        paddingBetweenCards = 10.dp,
        cardShape = RoundedCornerShape(12.dp),
        orientation = Orientation.Vertical(
            alignment = VerticalAlignment.BottomToTop,
            animationStyle = VerticalAnimationStyle.ToRight
        ),
        onCardClick = onCardClick
    ) {
        val summoner = summoners[it]
        SummonerItem(
            icon = summoner.icon,
            nickname = summoner.nickname.toText(),
            level = summoner.levelInfo,
            tierRank = summoner.tierRank,
            tierIcon = summoner.tier,
            isBookmark = summoner.isBookMarked,
            lpWinLose = summoner.lpWinLose,
            progress = summoner.miniSeries?.progress,
            onBookmarked = { onBookmark(summoner.id) }
        )
    }
}