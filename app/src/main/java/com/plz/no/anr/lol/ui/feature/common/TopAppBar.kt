package com.plz.no.anr.lol.ui.feature.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.plz.no.anr.lol.ui.theme.skyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    isBackPressVisible: Boolean = true,
    isDrawerVisible: Boolean = false,
    onDrawerClick: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    actions : @Composable (RowScope.() -> Unit) = {}
) {
    CenterAlignedTopAppBar(
        modifier = Modifier,
        title = {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = title,
                fontSize = 18.sp,
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = skyBlue,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
        ),
        navigationIcon = {
            if (isBackPressVisible) onBackPressed?.let {
                IconButton(
                    onClick = it
                ) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = "BackPress Button"
                    )
                }
            }
            if (isDrawerVisible) onDrawerClick?.let {
                IconButton(
                    onClick = it
                ) {
                    Icon(
                        Icons.Rounded.Menu,
                        contentDescription = "Drawer Button"
                    )
                }
            }
        },
        actions = actions,
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