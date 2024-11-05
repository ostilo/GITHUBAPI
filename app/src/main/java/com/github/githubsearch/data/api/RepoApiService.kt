package com.github.githubsearch.data.api

import com.github.githubsearch.data.dto.RepositoryDto
import com.github.githubsearch.domain.model.GitHubSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoApiService {

    @GET("/search/repositories")
    suspend fun getAllRepositories(@Query("per_page") perPage:Int, @Query("page") pageNo : Int): GitHubSearchResponse<RepositoryDto>
}