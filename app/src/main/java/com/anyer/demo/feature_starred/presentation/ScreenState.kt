package com.anyer.demo.feature_starred.presentation

import com.anyer.demo.feature_starred.model.GitHubRepo

data class ScreenState(
    val showLoading: Boolean = false,
    val repos: List<GitHubRepo> = emptyList()
)
