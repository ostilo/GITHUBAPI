package com.github.githubsearch.data.repository

import com.github.githubsearch.core.Result
import com.github.githubsearch.data.api.UserApiService
import com.github.githubsearch.data.mockUsersDto
import com.github.githubsearch.data.mockUsersListDto
import com.github.githubsearch.data.mockUsersRepoListDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    @MockK
    lateinit var userApiService: UserApiService

    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        userRepositoryImpl = UserRepositoryImpl(userApiService)
    }

    @Test
    fun `when getAllUsers is called and api service request is successful then repository send valid success data`() =
        runTest {
            coEvery { userApiService.getAllUsers(perPage = 5, pageNo = 1, q = "ostilo") } returns mockUsersListDto

            val result = userRepositoryImpl.getAllUsers(perPage = 5, pageNo = 1, searchCriteria = "ostilo")

            MatcherAssert.assertThat(
                result,
                CoreMatchers.instanceOf(Result.Success::class.java)
            )
        }

    @Test
    fun `when getAllUsers is called and api service request throws exception then repository send error`() =
        runTest {
            coEvery { userApiService.getUserDetails(username = "ostilo" ) } throws Exception()

            val result = userRepositoryImpl.getAllUsers(perPage = 5, pageNo = 1, searchCriteria = "ostilo")

            MatcherAssert.assertThat(
                result,
                CoreMatchers.instanceOf(Result.Error::class.java)
            )
        }


    @Test
    fun `when getUserDetails is called and api service request is successful then repository send valid success data`() =
        runTest {
            coEvery { userApiService.getUserDetails(username = "ostilo" ) } returns mockUsersDto

            val result = userRepositoryImpl.getUserDetails(username = "ostilo")

            MatcherAssert.assertThat(
                result,
                CoreMatchers.instanceOf(Result.Success::class.java)
            )
        }

    @Test
    fun `when getUserDetails is called and api service request throws exception then repository send error`() =
        runTest {
            coEvery { userApiService.getUserDetails(username = "ostilo" ) } throws Exception()

            val result = userRepositoryImpl.getUserDetails(username = "ostilo")

            MatcherAssert.assertThat(
                result,
                CoreMatchers.instanceOf(Result.Error::class.java)
            )
        }


    @Test
    fun `when getUsersRepos is called and api service request is successful then repository send valid success data`() =
        runTest {
            coEvery { userApiService.getUsersRepos(username = "ostilo", sort = "asc") } returns mockUsersRepoListDto

            val result = userRepositoryImpl.getUsersRepos(username = "ostilo")

            MatcherAssert.assertThat(
                result,
                CoreMatchers.instanceOf(Result.Success::class.java)
            )
        }

    @Test
    fun `when getUsersRepos is called and api service request throws exception then repository send error`() =
        runTest {
            coEvery { userApiService.getUsersRepos(username = "ostilo", sort = "asc" ) } throws Exception()

            val result = userRepositoryImpl.getUsersRepos(username = "ostilo")

            MatcherAssert.assertThat(
                result,
                CoreMatchers.instanceOf(Result.Error::class.java)
            )
        }

}