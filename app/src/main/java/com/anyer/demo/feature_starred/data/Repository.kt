package com.anyer.demo.feature_starred.data

import com.anyer.demo.feature_starred.model.GitHubRepo
import com.anyer.demo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    /**
     * Produces a flow of [List<GitHubRepo>]
     */
    fun gitHubReposFlow(): Flow<Resource<GitHubRepo>>
}
