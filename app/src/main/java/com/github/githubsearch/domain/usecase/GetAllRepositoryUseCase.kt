package com.github.githubsearch.domain.usecase

import com.github.githubsearch.domain.repository.RepoRepository

class GetAllRepositoryUseCase(
    private val reposRepository: RepoRepository
) {
    suspend fun execute(pageNo: Int, perPage:Int, searchCriteria: String) = reposRepository.getAllRepositories(pageNo = pageNo, perPage = perPage, searchCriteria = searchCriteria)
}