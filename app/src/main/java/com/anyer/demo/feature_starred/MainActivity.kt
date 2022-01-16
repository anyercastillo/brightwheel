package com.anyer.demo.feature_starred

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.anyer.demo.feature_starred.presentation.Screen
import com.anyer.demo.feature_starred.presentation.ScreenViewModel
import com.anyer.demo.ui.theme.DemoTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScreenContent(viewModel)
        }
    }
}

@Composable
private fun ScreenContent(viewModel: ScreenViewModel) {
    val screenState = viewModel.state.collectAsState()

    DemoTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Screen(state = screenState.value)
        }
    }
}