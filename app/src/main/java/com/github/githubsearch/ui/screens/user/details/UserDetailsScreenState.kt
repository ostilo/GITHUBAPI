package com.github.githubsearch.ui.screens.user.details

import com.github.githubsearch.domain.model.UsersRepo

sealed class UserDetailsScreenState {
    data object Loading : UserDetailsScreenState()
    data class Success(val listOfUserRepos: List<UsersRepo>) : UserDetailsScreenState()
    data class Error(val exception: Exception, val errorMessage : String) : UserDetailsScreenState()
}