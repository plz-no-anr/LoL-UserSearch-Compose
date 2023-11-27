package com.plznoanr.lol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.plznoanr.lol.core.designsystem.theme.LolUserSearchComposeTheme
import com.plznoanr.lol.ui.LoLApp
import com.plznoanr.lol.utils.NetworkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel : MainViewModel by viewModels()

    @Inject lateinit var networkManager: NetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var uiState: MainState by mutableStateOf(MainState.Loading)
        viewModel.mainState.onEach {
            uiState = it
        }.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).launchIn(lifecycleScope)

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                is MainState.Success -> true
                else -> false
            }
        }

        setContent {
            val isDarkTheme by viewModel.isDarkThemeState.collectAsStateWithLifecycle()
           LolUserSearchComposeTheme(
                darkTheme = isDarkTheme
           ) {
                LoLApp(networkManager = networkManager)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LolUserSearchComposeTheme {

    }
}