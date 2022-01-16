package com.anyer.demo.feature_starred.data

import com.anyer.demo.feature_starred.data.data_source.GitHubService
import com.anyer.demo.feature_starred.data.data_source.GitHubStarredRepos
import com.anyer.demo.feature_starred.model.GitHubRepo
import com.anyer.demo.utils.Resource
import com.anyer.demo.utils.apiCall
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val service: GitHubService,
    private val dispatcherIO: CoroutineDispatcher
) : Repository {
    override fun gitHubReposFlow(): Flow<Resource<GitHubRepo>> = flow {
        emit(Resource.Loading())

        val starredReposResource = apiCall(
            api = { service.fetchStarredRepos() },
            dispatcherIO = dispatcherIO
        )

        if (starredReposResource is Resource.Error) {
            emit(Resource.Error(message = starredReposResource.message))
            return@flow
        }

        if (starredReposResource is Resource.Success) {
            emitAll(fetchContributorsInParallelFlow(starredReposResource.data.items))
        }
    }

    /**
     * Creates [contributorsFetchInParallel] chunks for the given [starredRepos]
     * and fetches all contributors on each chunk. It emits responses per processed chunk.
     */
    private fun fetchContributorsInParallelFlow(
        starredRepos: List<GitHubStarredRepos>,
        contributorsFetchInParallel: Int = 3
    ): Flow<Resource<GitHubRepo>> = flow {
        val chunks = starredRepos.chunked(contributorsFetchInParallel)

        chunks.forEach { chunk ->
            val resources = processChunk(chunk)

            resources.forEach { resource ->
                emit(resource)
            }
        }
    }

    /**
     * Fetches a list of contributors in parallel. When all are done it generates a
     * list of [GitHubRepo] as a result of traversing the given [chunk] and selecting
     * the top contributor per starred repo.
     */
    private suspend fun processChunk(
        chunk: List<GitHubStarredRepos>
    ): List<Resource<GitHubRepo>> {
        val responses = withContext(dispatcherIO) {
            chunk.map { starredRepo ->
                fetchContributorAsync(starredRepo, service, dispatcherIO)
            }.awaitAll()
        }

        return responses
            .mapIndexed { index, resource ->
                when (resource) {
                    is Resource.Success -> {
                        val topContributor = resource.data.maxByOrNull { it.contributions }
                        val starredRepo = chunk[index]
                        val gitHubRepo = GitHubRepo(
                            name = starredRepo.name,
                            topContributor = topContributor?.login
                        )

                        Resource.Success(data = gitHubRepo)
                    }
                    else -> {
                        Resource.Error(message = resource.message.orEmpty())
                    }
                }
            }
    }
}

/**
 * Uses [async] coroutine to fetch a list of contributors for the given [starredRepo]
 */
private fun CoroutineScope.fetchContributorAsync(
    starredRepo: GitHubStarredRepos,
    service: GitHubService,
    dispatcherIO: CoroutineDispatcher
) = async {
    val repoOwner = starredRepo.owner.login
    val repoName = starredRepo.name

    apiCall(
        api = {
            service.fetchContributors(owner = repoOwner, repo = repoName)
        },
        dispatcherIO = dispatcherIO
    )
}