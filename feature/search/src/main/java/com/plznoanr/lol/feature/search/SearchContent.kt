package com.plznoanr.lol.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.plznoanr.lol.core.designsystem.icon.AppIcons
import com.plznoanr.lol.core.model.Search
import com.plznoanr.lol.core.model.toText
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchContent(
    modifier: Modifier = Modifier,
    data: ImmutableList<Search>,
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
        val searchBarColors = colors(containerColor = MaterialTheme.colorScheme.primary)
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = name,
                    onQueryChange = onNameChange,
                    onSearch = onSearch,
                    expanded = isActive,
                    onExpandedChange = onActiveChange,
                    placeholder = {
                        Text(text = "닉네임#태그")
                    },
                    leadingIcon = {
                        Icon(imageVector = AppIcons.Search, contentDescription = null)
                    },
                    colors = SearchBarDefaults.inputFieldColors().copy(
                        cursorColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                )
            },
            expanded = isActive,
            onExpandedChange = onActiveChange,
            colors = searchBarColors,
        ) {
            TextButton(
                modifier = Modifier
                    .align(Alignment.End),
                onClick = onDeleteAll,
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
                            nickname = search.nickname.toText(),
                            date = search.displayDateTime,
                            onSearch = onSearch,
                            onDelete = onDelete
                        )
                    }
                }
            }
        }

    }
}