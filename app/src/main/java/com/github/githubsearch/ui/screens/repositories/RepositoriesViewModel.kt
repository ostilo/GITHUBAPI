package com.github.githubsearch.ui.screens.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Repository
import com.github.githubsearch.domain.usecase.GetAllRepositoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.github.githubsearch.core.Result


class RepositoriesViewModel(
    private val getAllReposUseCase: GetAllRepositoryUseCase
) : ViewModel() {

    private val _repoState = MutableStateFlow<RepositoryScreenState>(RepositoryScreenState.Loading)
    val repoState: StateFlow<RepositoryScreenState> = _repoState.asStateFlow()

    fun fetchAllRepositories(pageNo : Int = 1, perPage : Int = 50) {
        viewModelScope.launch {
            val response = getAllReposUseCase.execute(pageNo = pageNo, perPage = perPage)
            handleGetAllRepoResponse(response)
        }
    }

    private fun handleGetAllRepoResponse(response: Result<GitHubSearchResponse<Repository>>) {
        when (response) {
            is Result.Success -> setRepoListState(RepositoryScreenState.Success(response.data))
            is Result.Error -> setRepoListState(RepositoryScreenState.Error(response.exception))
        }
    }

    private fun setRepoListState(state: RepositoryScreenState) {
        _repoState.update {
            state
        }
    }


}