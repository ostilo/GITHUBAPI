package com.github.githubsearch.domain.repository

import com.github.githubsearch.core.Result
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Users
import com.github.githubsearch.domain.model.UsersRepo

interface UsersRepository {
    suspend fun getAllUsers(pageNo: Int, perPage:Int, searchCriteria: String): Result<GitHubSearchResponse<Users>>
    suspend fun getUserDetails(username: String): Result<Users>
    suspend fun getUsersRepos(username: String): Result<List<UsersRepo>>

}