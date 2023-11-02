package com.plznoanr.lol.ui.feature.search.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plznoanr.lol.core.model.Search
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

@Composable
fun SearchItem(
    data: Search,
    onSearch: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSearch() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        Text(text = data.name)

        Spacer(modifier = Modifier.weight(1f))

        Text(text = data.displayDateTime)

        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
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
        Search(
            name = "Hide On Bush",
            date = LocalDateTime.now().toKotlinLocalDateTime()
        )
    )
}
