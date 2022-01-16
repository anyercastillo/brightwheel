package com.anyer.demo.feature_starred.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anyer.demo.feature_starred.model.GitHubRepo
import com.anyer.demo.ui.theme.Typography

private val repoContentPadding = PaddingValues(all = 16.dp)
private val repoCardPadding = PaddingValues(all = 8.dp)

@Composable
fun Screen(state: ScreenState) {
    LazyColumn {
        items(items = state.repos) { repo ->
            RepoCard(repo)
        }
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
            text = repo.topContributor,
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