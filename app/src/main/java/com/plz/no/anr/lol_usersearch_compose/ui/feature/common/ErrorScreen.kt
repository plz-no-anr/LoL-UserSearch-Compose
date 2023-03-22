package com.plz.no.anr.lol_usersearch_compose.ui.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plznoanr.lol_usersearch_compose.R

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errMsg: String = stringResource(id = R.string.error),
    onRetry: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        
        Spacer(modifier = Modifier.height(80.dp))

        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally),
            tint = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = errMsg.toErrorMsg(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = errMsg.toErrorDescription(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.height(40.dp))

        RetryView(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onRetry() }
        )

    }

}

@Composable
fun RetryView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
        .size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                text = stringResource(id = R.string.retry),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

}


@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen()
}

private fun String.toErrorDescription() = when (this.toErrorCode()) {
    in 200..299 -> "Success"
    403 -> ErrorType.FORBIDDEN.msg
    404 -> ErrorType.NOT_FOUND.msg
    else -> ErrorType.NETWORK.msg
}

private fun String.toErrorCode() = if (this.contains("/")) this.split("/")[0].toInt() else this
private fun String.toErrorMsg() = if (this.contains("/")) this.split("/")[1] else this
