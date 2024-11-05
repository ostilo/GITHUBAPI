package com.github.githubsearch.domain.repository

import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Repository
import com.github.githubsearch.core.Result

interface RepoRepository {
    suspend fun getAllRepositories(pageNo: Int, perPage:Int): Result<GitHubSearchResponse<Repository>>
}