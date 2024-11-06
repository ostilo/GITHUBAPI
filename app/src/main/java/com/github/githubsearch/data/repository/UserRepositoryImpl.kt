package com.github.githubsearch.data.repository

import com.github.githubsearch.core.Constants
import com.github.githubsearch.core.Result
import com.github.githubsearch.data.api.UserApiService
import com.github.githubsearch.data.mapper.toUserDomain
import com.github.githubsearch.data.mapper.toUserDomains
import com.github.githubsearch.data.mapper.toUserRepoDomain
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Users
import com.github.githubsearch.domain.model.UsersRepo
import com.github.githubsearch.domain.repository.UsersRepository

class UserRepositoryImpl(
    private val userApiService: UserApiService
) : UsersRepository {
    override suspend fun getAllUsers(pageNo: Int, perPage:Int, searchCriteria: String): Result<GitHubSearchResponse<Users>> {

        return try {
            Result.Success(userApiService.getAllUsers(perPage = perPage,pageNo = pageNo, q = searchCriteria).toUserDomain())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun getUserDetails(username: String): Result<Users> {
        return try {
            Result.Success(userApiService.getUserDetails(username = username).toUserDomains())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun getUsersRepos(username: String): Result<List<UsersRepo>> {
        return try {
            Result.Success(userApiService.getUsersRepos(username = username, sort = Constants.SORT_ORDER).toUserRepoDomain())
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

}