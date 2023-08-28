package com.plz.no.anr.lol.ui.feature.search.composables

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
import com.plz.no.anr.lol.R
import com.plz.no.anr.lol.domain.model.Search
import com.plz.no.anr.lol.ui.feature.search.SearchContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    data: List<Search>? = null,
    name: String? = null,
    onNameChange: (String) -> Unit,
    onIntent: (SearchContract.Intent) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TextField(
            value = name ?: "",
            onValueChange = onNameChange,
            label = { Text( text = stringResource(id = R.string.summoner_name) ) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            trailingIcon = {
                IconButton(
                    onClick = {
                        name?.let {
                            onIntent(SearchContract.Intent.Summoner.OnSearch(it))
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            )
        )

        TextButton(
            modifier = Modifier
                .align(Alignment.End),
            onClick = { onIntent(SearchContract.Intent.Search.OnDeleteAll) },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color.Black
            )
        ) {
            Text(text = stringResource(id = R.string.delete_all))
        }

        data?.let { list ->
            if (list.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                ) {
                    items(data) { search ->
                        SearchItem(
                            data = search,
                            onSearch = { onIntent(SearchContract.Intent.Summoner.OnSearch(search.name)) },
                            onDelete = { onIntent(SearchContract.Intent.Search.OnDelete(search.name)) }
                        )
                    }
                }
            }

        }

    }
}