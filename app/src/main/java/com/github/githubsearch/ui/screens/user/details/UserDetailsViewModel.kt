package com.github.githubsearch.ui.screens.user.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.githubsearch.core.GithubExtension.getStatusCode
import com.github.githubsearch.core.GithubExtension.getStatusMessage
import com.github.githubsearch.core.Result
import com.github.githubsearch.domain.model.UsersRepo
import com.github.githubsearch.domain.usecase.GetAllUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase
): ViewModel() {

    private val _userState = MutableStateFlow<UserDetailsScreenState>(UserDetailsScreenState.Loading)
    val userState: StateFlow<UserDetailsScreenState> = _userState.asStateFlow()

    fun fetchAllUserRepos(username : String) {
        viewModelScope.launch {
            _userState.update { UserDetailsScreenState.Loading }
            val response = getAllUsersUseCase.executeUserRepo(username = username)
            handleGetAllUserReposResponse(response)
        }
    }

    private fun handleGetAllUserReposResponse(response: Result<List<UsersRepo>>) {
        when (response) {
            is Result.Success -> setUserRepoListState(UserDetailsScreenState.Success(response.data))
            is Result.Error -> setUserRepoListState(UserDetailsScreenState.Error(response.exception, response.exception.getStatusCode().getStatusMessage()))
        }
    }

    private fun setUserRepoListState(state: UserDetailsScreenState) {
        _userState.update {
            state
        }
    }
}