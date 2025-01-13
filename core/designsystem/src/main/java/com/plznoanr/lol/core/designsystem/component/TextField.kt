package com.plznoanr.lol.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plznoanr.lol.core.designsystem.theme.LolUserSearchTheme

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    enabled: Boolean = true,
    placeholder: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    singleLine: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            enabled = enabled,
            value = value,
            singleLine = singleLine,
            onValueChange = onValueChange,
//            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            decorationBox = { innerTextField ->
                if (value.text.isBlank()) {
                    Text(
                        text = placeholder,
//                        style = textStyle
                    )
                } else innerTextField()
            }
        )
}


@Preview
@Composable
private fun PreviewInputTextField() {
    LolUserSearchTheme {
        val (value, onValueChange) = remember { mutableStateOf(TextFieldValue()) }
        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager = focusManager)
        ) {
            InputTextField(
                modifier = Modifier.padding(16.dp),
                value = value,
                placeholder = "닉네임을 입력하세요.",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                onValueChange = onValueChange
            )
        }
    }
}