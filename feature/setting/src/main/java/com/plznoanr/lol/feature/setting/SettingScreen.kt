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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plznoanr.lol.core.designsystem.component.DefaultDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun SettingRoute(
    onShowSnackbar: suspend (String) -> Boolean,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    SettingScreen(
        state = state,
        sideEffectFlow = viewModel.sideEffectFlow,
        onThemeChange = { coroutineScope.launch { viewModel.onEvent(OnThemeChange(it)) } },
        onKeyChange = { coroutineScope.launch { viewModel.onEvent(OnKeyChange(it)) } },
        onShowSnackbar = onShowSnackbar
    )

}

@Composable
internal fun SettingScreen(
    state: UiState = UiState(),
    sideEffectFlow: Flow<SideEffect>,
    onThemeChange: (Boolean) -> Unit,
    onKeyChange: (String) -> Unit,
    onShowSnackbar: suspend (String) -> Boolean,
) {
    val coroutineScope = rememberCoroutineScope()
    val (keyQuery, onQueryChange) = remember {
        mutableStateOf("")
    }
    var isShowKeyChangeDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        sideEffectFlow.onEach { sideEffect ->
            when (sideEffect) {
                is OnShowSnackbar -> coroutineScope.launch {
                    onShowSnackbar(sideEffect.message)
                }
            }
        }.collect()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Profile(
            nickname = state.profile?.nickname ?: "",
        )

        Spacer(modifier = Modifier.height(20.dp))

        ApiKey(
            key = state.apiKey,
            onShowDialog = { isShowKeyChangeDialog = true }
        )

        Spacer(modifier = Modifier.height(20.dp))

        ThemeChange(
            isDarkTheme = state.isDarkTheme,
            onThemeChange = onThemeChange
        )
    }

    if (isShowKeyChangeDialog) {
        KeyChangeDialog(
            key = keyQuery,
            onQueryChange = onQueryChange,
            onKeyChange = onKeyChange,
            onDismiss = { isShowKeyChangeDialog = false }
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
                .background(Color.White)
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

            Button(onClick = { onKeyChange(key) }) {
                Text(text = "변경", color = MaterialTheme.colorScheme.secondary)
            }
        }
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


@Preview
@Composable
private fun SettingScreenPreview() {
}