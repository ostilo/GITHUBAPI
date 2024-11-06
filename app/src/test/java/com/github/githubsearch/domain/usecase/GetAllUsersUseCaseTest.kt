package com.github.githubsearch.domain.usecase

import com.github.githubsearch.core.Result
import com.github.githubsearch.data.mapper.toUserDomain
import com.github.githubsearch.data.mapper.toUserDomains
import com.github.githubsearch.data.mapper.toUserRepoDomain
import com.github.githubsearch.data.mockUsersDto
import com.github.githubsearch.data.mockUsersListDto
import com.github.githubsearch.data.mockUsersRepoListDto
import com.github.githubsearch.domain.repository.UsersRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

internal class GetAllUsersUseCaseTest {

    @MockK
    val repository: UsersRepository = mockk()

    private lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @Before
    fun setup() {
        getAllUsersUseCase = GetAllUsersUseCase(repository)
    }

    @Test
    fun `When network request is successful then getAllUsers returns success`() =
        runTest {
            coEvery { repository.getAllUsers(pageNo = 1, perPage = 50, searchCriteria = "android") } returns Result.Success(
                mockUsersListDto.toUserDomain())

            val result = getAllUsersUseCase.execute(pageNo = 1, perPage = 50, searchCriteria = "android")

            MatcherAssert.assertThat(result, CoreMatchers.instanceOf(Result.Success::class.java))
        }

    @Test
    fun `Given valid params when network request fails then getAllUsers return failure`() =
        runTest {
            coEvery { repository.getAllUsers(pageNo = 1, perPage = 50, searchCriteria = "android") } returns Result.Error(Exception())

            val result = getAllUsersUseCase.execute(pageNo = 1, perPage = 50, searchCriteria = "android")

            MatcherAssert.assertThat(result, CoreMatchers.instanceOf(Result.Error::class.java))
        }


    @Test
    fun `When network request is successful then getUserDetails returns success`() =
        runTest {
            coEvery { repository.getUserDetails(username = "ostilo") } returns Result.Success(
                mockUsersDto.toUserDomains())

            val result = getAllUsersUseCase.execute(username = "ostilo")

            MatcherAssert.assertThat(result, CoreMatchers.instanceOf(Result.Success::class.java))
        }

    @Test
    fun `Given valid params when network request fails then getUserDetails return failure`() =
        runTest {
            coEvery { repository.getUserDetails(username = "ostilo") } returns Result.Error(Exception())

            val result = getAllUsersUseCase.execute(username = "ostilo")

            MatcherAssert.assertThat(result, CoreMatchers.instanceOf(Result.Error::class.java))
        }

    @Test
    fun `When network request is successful then getUsersRepos returns success`() =
        runTest {
            coEvery { repository.getUsersRepos(username = "ostilo") } returns Result.Success(
                mockUsersRepoListDto.toUserRepoDomain())

            val result = getAllUsersUseCase.executeUserRepo( username = "ostilo")

            MatcherAssert.assertThat(result, CoreMatchers.instanceOf(Result.Success::class.java))
        }

    @Test
    fun `Given valid params when network request fails then getUsersRepos return failure`() =
        runTest {
            coEvery { repository.getUsersRepos(username = "ostilo") } returns Result.Error(Exception())

            val result = getAllUsersUseCase.executeUserRepo(username = "ostilo")

            MatcherAssert.assertThat(result, CoreMatchers.instanceOf(Result.Error::class.java))
        }

}