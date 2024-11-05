package com.github.githubsearch.data.repository

import com.github.githubsearch.core.Result
import com.github.githubsearch.data.api.RepoApiService
import com.github.githubsearch.data.mapper.toDomain
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Repository
import com.github.githubsearch.domain.repository.RepoRepository

class RepoRepositoryImpl(
    private val repoApiService: RepoApiService
) : RepoRepository{
    override suspend fun getAllRepositories(pageNo: Int, perPage:Int): Result<GitHubSearchResponse<Repository>> {

        return try {
            Result.Success(repoApiService.getAllRepositories(perPage = perPage,pageNo = pageNo).toDomain())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

}