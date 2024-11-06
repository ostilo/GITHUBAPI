package com.github.githubsearch.ui.screens.repositories
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Repository

sealed class RepositoryScreenState {
    data object Loading : RepositoryScreenState()
    data object Default : RepositoryScreenState()
    data class Success(val listOfRepos: GitHubSearchResponse<Repository>) : RepositoryScreenState()
    data class Error(val exception: Exception, val errorMessage : String) : RepositoryScreenState()
}