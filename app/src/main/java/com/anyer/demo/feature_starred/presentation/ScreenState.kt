package com.anyer.demo.feature_starred.presentation

import com.anyer.demo.feature_starred.model.GitHubRepo

data class ScreenState(
    val repos: List<GitHubRepo> = emptyList()
)
