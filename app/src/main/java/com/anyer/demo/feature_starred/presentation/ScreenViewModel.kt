package com.anyer.demo.feature_starred.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anyer.demo.feature_starred.data.Repository
import com.anyer.demo.feature_starred.model.GitHubRepo
import com.anyer.demo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * [ScreenViewModel] exposes the state [ScreenState]
 */
@HiltViewModel
class ScreenViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState())
    val state = _state.asStateFlow()

    init {
        repository
            .gitHubReposFlow()
            .onEach(::processGitHubReposFlow)
            .launchIn(viewModelScope)
    }

    /**
     * Produces a new state based on the given [resource]
     */
    private fun processGitHubReposFlow(resource: Resource<GitHubRepo>) {
        when (resource) {
            is Resource.Error -> {
                Log.e("ScreenViewModel", "processGitHubReposFlow: Error ${resource.message}")
            }
            is Resource.Loading -> {
                _state.value = _state.value.copy(showLoading = true)
            }
            is Resource.Success -> {
                val repos = state.value.repos.toMutableList()
                repos.add(resource.data)
                _state.value = _state.value.copy(
                    repos = repos,
                    showLoading = false
                )
            }
        }
    }
}