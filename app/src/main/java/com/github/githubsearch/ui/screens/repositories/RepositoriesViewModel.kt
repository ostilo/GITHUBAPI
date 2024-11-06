package com.github.githubsearch.ui.screens.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.githubsearch.core.Constants
import com.github.githubsearch.core.GithubExtension.getStatusCode
import com.github.githubsearch.core.GithubExtension.getStatusMessage
import com.github.githubsearch.core.Result
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Repository
import com.github.githubsearch.domain.usecase.GetAllRepositoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class RepositoriesViewModel(
    private val getAllReposUseCase: GetAllRepositoryUseCase
) : ViewModel() {

    private val _repoState = MutableStateFlow<RepositoryScreenState>(RepositoryScreenState.Default)
    val repoState: StateFlow<RepositoryScreenState> = _repoState.asStateFlow()

    fun fetchAllRepositories(pageNo : Int = Constants.DEFAULT_PAGE_NUMBER, perPage : Int = Constants.DEFAULT_PAGE_COUNT, searchCriteria : String = "") {
        viewModelScope.launch {
            _repoState.update { RepositoryScreenState.Loading }
            val response = getAllReposUseCase.execute(pageNo = pageNo, perPage = perPage, searchCriteria = searchCriteria)
            handleGetAllRepoResponse(response)
        }
    }


    private fun handleGetAllRepoResponse(response: Result<GitHubSearchResponse<Repository>>) {
        when (response) {
            is Result.Success -> {
                setRepoListState(RepositoryScreenState.Success(response.data))
            }
            is Result.Error -> {
                setRepoListState(RepositoryScreenState.Error(response.exception, response.exception.getStatusCode().getStatusMessage()))
            }
        }
    }

    private fun setRepoListState(state: RepositoryScreenState) {
        _repoState.update {
            state
        }
    }

}