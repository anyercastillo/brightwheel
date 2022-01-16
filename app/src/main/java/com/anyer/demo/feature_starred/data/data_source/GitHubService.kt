package com.anyer.demo.feature_starred.data.data_source

import com.anyer.demo.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

class GitHubUser(
    val login: String
)

class GitHubStarredRepos(
    val name: String,
    val owner: GitHubUser
)

class GitHubStarredReposResponse(
    val items: List<GitHubStarredRepos>
)

class GitHubContributor(
    val login: String,
    val contributions: Int
)

interface GitHubService {
    /**
     * Fetches the first 100 starred projects.
     * By default GitHub returns only 30 projects.
     */
    @Headers("Authorization: token ${BuildConfig.GITHUB_OAUTH2_TOKEN}")
    @GET("search/repositories?q=stars:>0&per_page=100")
    suspend fun fetchStarredRepos(): GitHubStarredReposResponse

    /**
     * Fetches a list of contributors for the given [repo]
     */
    @Headers("Authorization: token ${BuildConfig.GITHUB_OAUTH2_TOKEN}")
    @GET("repos/{owner}/{repo}/contributors")
    suspend fun fetchContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): List<GitHubContributor>
}