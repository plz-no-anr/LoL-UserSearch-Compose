package com.plznoanr.lol.feature.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plznoanr.lol.core.designsystem.component.DefaultDialog
import com.plznoanr.lol.core.designsystem.theme.LolUserSearchTheme

@Composable
internal fun SettingContent(
    nickname: String?,
    apiKey: String,
    keyQuery: String,
    isDarkTheme: Boolean,
    isShowDialog: Boolean,
    onShowDialog: (Boolean) -> Unit,
    onThemeChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onKeyChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Profile(
            nickname = nickname ?: "",
        )

        Spacer(modifier = Modifier.height(20.dp))

        ApiKey(
            key = apiKey,
            onShowDialog = { onShowDialog(true) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        ThemeChange(
            isDarkTheme = isDarkTheme,
            onThemeChange = onThemeChange
        )
    }

    if (isShowDialog) {
        KeyChangeDialog(
            key = keyQuery,
            onQueryChange = onQueryChange,
            onKeyChange = onKeyChange,
            onDismiss = { onShowDialog(false) }
        )
    }
}

@Preview
@Composable
private fun PreviewSettingContent() {
    LolUserSearchTheme {
        SettingContent(
            nickname = "닉네임",
            apiKey = "키",
            keyQuery = "키",
            isDarkTheme = true,
            isShowDialog = true,
            onShowDialog = {},
            onThemeChange = {},
            onQueryChange = {},
            onKeyChange = {}
        )
    }
}

@Composable
private fun Profile(
    nickname: String,
) {
    Column {
        TitleText(text = "내 정보")

        Spacer(modifier = Modifier.height(5.dp))

        if (nickname.isNotEmpty()) ItemText(text = nickname)
    }
}

@Composable
private fun ApiKey(
    key: String,
    onShowDialog: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TitleText(text = "API Key")

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = onShowDialog,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "변경", color = MaterialTheme.colorScheme.onTertiaryContainer)
            }
        }

        if (key.isNotEmpty()) ItemText(text = key)
    }

}

@Composable
private fun ThemeChange(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    Column {
        TitleText(text = "테마 변경")

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(7.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(10.dp)
        ) {
            Text(text = "다크테마", color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkTheme,
                onCheckedChange = onThemeChange,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colorScheme.inversePrimary,
                    checkedThumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}


@Composable
private fun KeyChangeDialog(
    key: String,
    onQueryChange: (String) -> Unit,
    onKeyChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    DefaultDialog(onDismiss = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = key,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(text = "변경할 키를 입력해 주세요")
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.secondary
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onKeyChange(key)
                    onDismiss()
                }
            ) {
                Text(text = "변경", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewKeyChangeDialog() {
    var key by remember { mutableStateOf("") }
    LolUserSearchTheme {
        KeyChangeDialog(
            key = key,
            onQueryChange = {},
            onKeyChange = {
                key = it
            },
            onDismiss = {}
        )
    }
}

@Composable
private fun TitleText(
    text: String
) = Text(
    text = text,
    fontSize = 16.sp,
    color = MaterialTheme.colorScheme.onTertiaryContainer
)

@Composable
private fun ItemText(
    text: String
) = Text(
    text = text,
    modifier = Modifier
        .clip(RoundedCornerShape(7.dp))
        .background(MaterialTheme.colorScheme.primary)
        .padding(10.dp)
)