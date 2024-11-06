package com.github.githubsearch.data.api

import com.github.githubsearch.data.dto.UsersDto
import com.github.githubsearch.data.dto.UsersRepoDto
import com.github.githubsearch.domain.model.GitHubSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/* User related ApiService */
interface UserApiService {
    @GET("/search/users")
    suspend fun getAllUsers(@Query("per_page") perPage:Int, @Query("page") pageNo : Int, @Query("q") q: String): GitHubSearchResponse<UsersDto>

    @GET("/users/{username}")
    suspend fun getUserDetails(@Path("username") username: String): UsersDto

    @GET("/users/{username}/repos")
    suspend fun getUsersRepos(@Path("username") username: String, @Query("sort") sort : String): List<UsersRepoDto>

}