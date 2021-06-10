package com.example.app_test.datasource

import com.example.app_test.data.model.SuccessResponse
import com.example.app_test.data.remote.GitHubDataSourceImpl
import com.example.app_test.BaseTest
import com.example.app_test.data.remote.service.GitHubService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserDataSourceTest : BaseTest() {

    private val service = mockk<GitHubService>()

    private val datasource = GitHubDataSourceImpl(service)

    @Test
    fun whenGetUserIsEmpty() = runBlockingTest {
        val expectedResponse = SuccessResponse(githubResponseEmpty)

        coEvery { service.getUsers(page = 1) } returns Response.success(githubResponseEmpty)
        val users = datasource.getUsers(1)

        assertEquals(expectedResponse, users)
    }

    @Test
    fun whenGetUserSuccess() = runBlockingTest {
        val expectedResponse = SuccessResponse(githubResponse)

        coEvery { service.getUsers(page = 1) } returns Response.success(githubResponse)
        val users = datasource.getUsers(1)

        assertEquals(expectedResponse, users)
    }


}