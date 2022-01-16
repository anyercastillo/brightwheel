package com.anyer.demo.feature_starred.presentation

import androidx.lifecycle.ViewModel
import com.anyer.demo.feature_starred.model.GitHubRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private val defaultScreenState = ScreenState(
    repos = listOf(
        GitHubRepo(name = "brightwheel", "Jane Doe"),
        GitHubRepo(name = "kotlin", "Jhon Doe"),
        GitHubRepo(name = "bbt", "Sheldon Cooper"),
    )
)

/**
 * [ScreenViewModel] exposes the state [ScreenState]
 */
@HiltViewModel
class ScreenViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(defaultScreenState)
    val state = _state.asStateFlow()
}