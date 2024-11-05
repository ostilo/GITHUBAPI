package com.github.githubsearch.ui.screens.repositories

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.github.githubsearch.DefaultSearchStateContent
import com.github.githubsearch.GithubSearchHeader
import com.github.githubsearch.R

@Composable
fun RepositoriesScreen() {
    GithubSearchHeader(
        title = R.string.repositories,
        placeHolder = R.string.repo_place_holder,
        screen = { RepositoriesContent() },
        onSearchClicked = {

    })
}

@Composable
fun RepositoriesContent(){

    DefaultSearchStateContent(title = R.string.search_repo_message)
}