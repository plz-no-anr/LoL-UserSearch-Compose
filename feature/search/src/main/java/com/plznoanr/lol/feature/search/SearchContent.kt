package com.plznoanr.lol.feature.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.plznoanr.lol.core.designsystem.icon.AppIcons
import com.plznoanr.lol.core.model.Search
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchContent(
    modifier: Modifier = Modifier,
    data: PersistentList<Search> = persistentListOf(),
    isActive: Boolean = false,
    name: String = "",
    onNameChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    onDelete: (String) -> Unit,
    onDeleteAll: () -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SearchBar(
            query = name,
            onQueryChange = onNameChange,
            onSearch = onSearch,
            active = isActive,
            onActiveChange = onActiveChange,
            placeholder = {
                Text(text = "닉네임#태그")
            },
            leadingIcon = {
                Icon(imageVector = AppIcons.Search, contentDescription = null)
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            TextButton(
                modifier = Modifier
                    .align(Alignment.End),
                onClick = onDeleteAll ,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Black
                )
            ) {
                Text(text = stringResource(id = R.string.delete_all))
            }

            if (data.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                ) {
                    items(data) { search ->
                        SearchItem(
                            data = search,
                            onSearch = onSearch,
                            onDelete = onDelete
                        )
                    }
                }
            }
        }

    }
}