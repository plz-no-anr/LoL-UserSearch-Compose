package com.plznoanr.lol.feature.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plznoanr.lol.core.designsystem.component.IconImage
import com.plznoanr.lol.core.designsystem.component.LazyColumnIndicator
import com.plznoanr.lol.core.designsystem.icon.AppIcons
import com.plznoanr.lol.core.designsystem.theme.SkyBlue
import com.plznoanr.lol.core.model.Profile
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.model.toText
import com.plznoanr.lol.core.ui.EmptyBox
import com.plznoanr.lol.core.ui.summoner.SummonerItem
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    data: ImmutableList<Summoner>,
    isRefreshing: Boolean = false,
    isLoadNextPage: Boolean = false,
    isSortedBookmark: Boolean = false,
    lazyListState: LazyListState,
    onRefresh: () -> Unit = {},
    onBookmarked: (String) -> Unit = {},
    onDeleteAll: () -> Unit = {},
    onSortedBookmark: (Boolean) -> Unit = {}
) {
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (data.isNotEmpty()) {
            Row(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = isSortedBookmark,
                    onClick = { onSortedBookmark(!isSortedBookmark) },
                    label = { Text(text = "북마크 정렬") },
                    colors = FilterChipDefaults.filterChipColors().copy(
                        selectedContainerColor = MaterialTheme.colorScheme.inversePrimary
                    ),
//                    trailingIcon = {
//                        Icon(
//                            imageVector = AppIcons.BookMark,
//                            contentDescription = null,
//                            tint = MaterialTheme.colorScheme.secondary
//                        )
//                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(id = R.string.delete_all),
                    modifier = Modifier
                        .clickable { onDeleteAll() },
                )
            }

        }

        Box(
            modifier = Modifier
                .pullRefresh(state = pullRefreshState)
        ) {
            if (data.isNotEmpty()) {
                LazyColumn(
                    state = lazyListState,
                ) {
                    items(data) {
                        SummonerItem(
                            icon = it.icon,
                            nickname = it.nickname.toText(),
                            level = it.levelInfo,
                            tierRank = it.tierRank,
                            tierIcon = it.tier,
                            isBookmark = it.isBookMarked,
                            lpWinLose = it.lpWinLose,
                            progress = it.miniSeries?.progress,
                            onBookmarked = { onBookmarked(it.id) }
                        )
                    }
                    item {
                        if (isLoadNextPage) {
                            LazyColumnIndicator(
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                EmptyBox()
            }

            PullRefreshIndicator(
                isRefreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }

    }
}

@Composable
fun Drawers(
    data: Profile,
    apiKey: String?,
    onGetKey: () -> Unit,
    onAddKey: (String) -> Unit,
    onDeleteKey: () -> Unit
) {

    IconImage(
        modifier = Modifier
            .padding(top = 30.dp, start = 16.dp, bottom = 16.dp)
            .size(size = 100.dp)
            .clip(RoundedCornerShape(10)),
        url = data.icon,
        scale = ContentScale.FillWidth,
    )

    Text(
        text = data.nickname,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 16.dp)
    )

    Text(
        text = data.levelText,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 16.dp)
    )

    Divider(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    )

    KeyView(
        apiKey = apiKey,
        onGetKey = onGetKey,
        onAddKey = onAddKey,
        onDeleteKey = onDeleteKey
    )

}


@Composable
private fun KeyView(
    apiKey: String?,
    onGetKey: () -> Unit,
    onAddKey: (String) -> Unit,
    onDeleteKey: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
            )
    ) {
        Text(
            text = stringResource(id = R.string.api_key),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(16.dp))

        apiKey?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                OutlinedButton(onClick = onGetKey) {
                    Text(
                        text = stringResource(id = R.string.get_key),
                        color = SkyBlue
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(onClick = onDeleteKey) {
                    Text(
                        text = stringResource(id = R.string.delete),
                        color = SkyBlue
                    )
                }
            }

        } ?: run {
            KeyAddView { onAddKey(it) }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KeyAddView(
    onAddKey: (String) -> Unit
) {
    var textState by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .padding(end = 16.dp)
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = {
                Text(
                    text = stringResource(id = R.string.add_key),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier
                .weight(1f),
            colors = TextFieldDefaults.colors(
//                focusedBorderColor = MaterialTheme.colorScheme.primary,
//                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
//                textColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.width(4.dp))

        IconButton(
            onClick = { onAddKey(textState) },
            Modifier
                .padding(top = 8.dp)
        ) {
            Icon(
                imageVector = AppIcons.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

