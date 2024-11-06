package com.github.githubsearch.ui.screens.user

import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Users

sealed class UserScreenState {
    data object Loading : UserScreenState()
    data object Default : UserScreenState()
    data class Success(val listOfUsers: GitHubSearchResponse<Users>) : UserScreenState()
    data class Error(val exception: Exception, val errorMessage : String) : UserScreenState()
}