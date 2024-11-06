package com.github.githubsearch.data.repository


import com.github.githubsearch.data.api.RepoApiService
import com.github.githubsearch.data.mockRepositoryListDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import com.github.githubsearch.core.Result

internal class RepoRepositoryImplTest {
    @MockK
    lateinit var repoApiService: RepoApiService

    private lateinit var repoRepositoryImpl: RepoRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repoRepositoryImpl = RepoRepositoryImpl(repoApiService)
    }

    @Test
    fun `when getAllRepositories is called and api service request is successful then repository send valid success data`() =
        runTest {
            coEvery { repoApiService.getAllRepositories(perPage = 5, pageNo = 1, q = "android", sort = "asc") } returns mockRepositoryListDto

            val result = repoRepositoryImpl.getAllRepositories(perPage = 5, pageNo = 1, searchCriteria = "android")

            assertThat(
                result,
                instanceOf(Result.Success::class.java)
            )
        }


    @Test
    fun `when getAllRepositories is called and api service request throws exception then repository send error`() =
        runTest {
            coEvery { repoApiService.getAllRepositories(perPage = 5, pageNo = 1, q = "android", sort = "asc") } throws Exception()

            val result = repoRepositoryImpl.getAllRepositories(perPage = 5, pageNo = 1, searchCriteria = "android")

            assertThat(
                result,
                instanceOf(Result.Error::class.java)
            )
        }

}