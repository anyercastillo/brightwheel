package com.anyer.demo.feature_starred.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anyer.demo.feature_starred.model.GitHubRepo
import com.anyer.demo.ui.theme.Typography

private val repoContentPadding = PaddingValues(all = 16.dp)
private val repoCardPadding = PaddingValues(all = 8.dp)

@Composable
fun Screen(state: ScreenState) {
    if (state.showLoading) {
        Loading()
    }

    LazyColumn {
        items(items = state.repos) { repo ->
            RepoCard(repo)
        }
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun RepoCard(repo: GitHubRepo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(repoCardPadding),
    ) {
        Repo(repo = repo)
    }
}

@Composable
private fun Repo(repo: GitHubRepo) {
    Column(modifier = Modifier.padding(repoContentPadding)) {
        Text(
            text = repo.name,
            style = Typography.h5
        )
        Text(
            text = repo.topContributor ?: "none",
            style = Typography.body1
        )
    }
}


@Preview
@Composable
private fun RepoCardPreview() {
    RepoCard(
        repo = GitHubRepo(name = "brightwheel", topContributor = "Jane Doe")
    )
}