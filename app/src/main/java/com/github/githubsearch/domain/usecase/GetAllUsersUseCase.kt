package com.github.githubsearch.domain.usecase

import com.github.githubsearch.domain.repository.UsersRepository

class GetAllUsersUseCase(
    private val usersRepository: UsersRepository
) {
    suspend fun execute(pageNo: Int, perPage:Int, searchCriteria: String) = usersRepository.getAllUsers(pageNo = pageNo, perPage = perPage, searchCriteria = searchCriteria)
    suspend fun execute(username: String) = usersRepository.getUserDetails(username = username)
    suspend fun executeUserRepo(username: String) = usersRepository.getUsersRepos(username = username)

}