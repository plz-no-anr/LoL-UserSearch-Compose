package com.plz.no.anr.lol_usersearch_compose.ui.feature.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    isBackPressVisible: Boolean = true,
    onBackPressed: () -> Unit = {},
    actions : @Composable (RowScope.() -> Unit)
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = title,
                fontSize = 18.sp,
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
        ),
        navigationIcon = {
            if (isBackPressVisible) IconButton(
                onClick = { onBackPressed() }
            ) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = "BackPress Button"
                )
            }
        },
        actions = actions
    )
}



@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar(
        "목록",
        onBackPressed = {},
        actions = {}
    )
}