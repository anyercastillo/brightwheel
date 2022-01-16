package com.anyer.demo.feature_starred

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.anyer.demo.feature_starred.model.GitHubRepo
import com.anyer.demo.feature_starred.presentation.Screen
import com.anyer.demo.feature_starred.presentation.ScreenState
import com.anyer.demo.ui.theme.DemoTheme
import dagger.hilt.android.AndroidEntryPoint

private val testScreenState = ScreenState(
    repos = listOf(
        GitHubRepo(name = "brightwheel", "Jane Doe"),
        GitHubRepo(name = "kotlin", "Jhon Doe"),
        GitHubRepo(name = "bbt", "Sheldon Cooper"),
    )
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScreenContent()
        }
    }
}

@Composable
private fun ScreenContent() {
    DemoTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Screen(state = testScreenState)
        }
    }
}