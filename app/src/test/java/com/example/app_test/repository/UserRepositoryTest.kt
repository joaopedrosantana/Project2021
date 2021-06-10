package com.example.app_test.repository

import com.example.app_test.BaseTest
import com.example.app_test.data.model.ErrorResponse
import com.example.app_test.data.model.GitHubResponse
import com.example.app_test.data.model.RequestError
import com.example.app_test.data.model.SuccessResponse
import com.example.app_test.data.remote.GitHubDataSource
import com.example.app_test.data.repository.GitHubRepositoryImpl

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest : BaseTest() {

    private val dataSource = mockk<GitHubDataSource>()

    private val repository = GitHubRepositoryImpl(dataSource)

    @Test
    fun whenGetUserIsEmpty() = runBlockingTest {
        val expectedResponse = SuccessResponse(githubResponseEmpty)

        coEvery { dataSource.getUsers(1) } returns SuccessResponse(githubResponseEmpty)
        val users = repository.getRepositories(1)

        assertEquals(expectedResponse, users)
    }

    @Test
    fun whenGetUserSuccess() = runBlockingTest {
        val expectedResponse = SuccessResponse(githubResponse)

        coEvery { dataSource.getUsers(1) } returns SuccessResponse(githubResponse)
        val users = repository.getRepositories(1)

        assertEquals(expectedResponse, users)
    }

    @Test
    fun whenGetUserError() = runBlockingTest {
        val requestError = RequestError(0, "Error")
        val expectedResponse = ErrorResponse<GitHubResponse>(requestError);

        coEvery { dataSource.getUsers(1) } returns ErrorResponse(requestError)
        val users = repository.getRepositories(1)

        assertEquals(expectedResponse, users)
    }


}