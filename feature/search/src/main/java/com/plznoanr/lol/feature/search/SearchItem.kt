package com.plznoanr.lol.feature.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plznoanr.lol.core.designsystem.icon.AppIcons

@Composable
internal fun SearchItem(
    nickname: String,
    date: String,
    onSearch: (String) -> Unit = {},
    onDelete: (String) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSearch(nickname) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        Text(text = nickname)

        Spacer(modifier = Modifier.weight(1f))

        Text(text = date)

        IconButton(onClick = { onDelete(nickname) }) {
            Icon(
                imageVector = AppIcons.Close,
                contentDescription = null
            )
        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun SearchItemPreview() {
    SearchItem(
        nickname = "",
        date = "asd"
    )
}
