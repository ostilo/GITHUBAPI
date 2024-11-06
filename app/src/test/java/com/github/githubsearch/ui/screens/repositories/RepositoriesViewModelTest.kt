package com.github.githubsearch.ui.screens.repositories

import com.github.githubsearch.core.Constants
import com.github.githubsearch.domain.usecase.GetAllRepositoryUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.github.githubsearch.core.Result
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class RepositoriesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var getAllRepoUseCase: GetAllRepositoryUseCase

    private lateinit var repoViewModel: RepositoriesViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        getAllRepoUseCase = mockk()
        repoViewModel = RepositoriesViewModel(getAllRepoUseCase)
    }

    @After
    fun tearDown() {
      Dispatchers.resetMain()
    }

    @Test
    fun `should show exception when network request is unsuccessful`() = runTest {
        // Given
        val exception = HttpException(
            Response.error<Any>(
                503,
                "Response.error()".toResponseBody()
            )
        )

        coEvery {
            getAllRepoUseCase.execute(
                pageNo = Constants.DEFAULT_PAGE_NUMBER,
                perPage = Constants.DEFAULT_PAGE_COUNT,
                searchCriteria = ""
            )
        } returns Result.Error(exception)

        // When
        repoViewModel.fetchAllRepositories()

        // Then
        assertEquals(RepositoryScreenState.Default, repoViewModel.repoState.value)

        // Advance time to complete coroutine
        testDispatcher.scheduler.advanceUntilIdle()

        // Finally, ensure all coroutines complete
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify final state
        val finalState = repoViewModel.repoState.value
        assertTrue(finalState is RepositoryScreenState.Error)
        assertEquals(exception, (finalState as RepositoryScreenState.Error).exception)

    }



}