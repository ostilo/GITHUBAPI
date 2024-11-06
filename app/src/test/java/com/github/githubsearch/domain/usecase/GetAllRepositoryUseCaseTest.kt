package com.github.githubsearch.domain.usecase

import com.github.githubsearch.core.Result
import com.github.githubsearch.data.mapper.toDomain
import com.github.githubsearch.data.mockRepositoryListDto
import com.github.githubsearch.domain.repository.RepoRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test


internal class GetAllRepositoryUseCaseTest {

    @MockK
    val repository: RepoRepository = mockk()

    private lateinit var getAllRepoUseCase: GetAllRepositoryUseCase

    @Before
    fun setup() {
        getAllRepoUseCase = GetAllRepositoryUseCase(repository)
    }

    @Test
    fun `When network request is successful then getAllRepositories returns success`() =
        runTest {
            coEvery { repository.getAllRepositories(pageNo = 1, perPage = 50, searchCriteria = "android") } returns Result.Success(mockRepositoryListDto.toDomain())

            val result = getAllRepoUseCase.execute(pageNo = 1, perPage = 50, searchCriteria = "android")

            MatcherAssert.assertThat(result, CoreMatchers.instanceOf(Result.Success::class.java))
        }

    @Test
    fun `Given valid params when network request fails then getAllRepositories return failure`() =
        runTest {
            coEvery { repository.getAllRepositories(pageNo = 1, perPage = 50, searchCriteria = "android") } returns Result.Error(Exception())

            val result = getAllRepoUseCase.execute(pageNo = 1, perPage = 50, searchCriteria = "android")

            MatcherAssert.assertThat(result, CoreMatchers.instanceOf(Result.Error::class.java))
        }

}