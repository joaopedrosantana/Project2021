package com.example.app_test

import com.example.app_test.data.model.GitHubResponse
import com.example.app_test.data.model.RepositoryModel
import com.example.app_test.data.model.User
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
open class BaseTest {

    private val testDispatcher = TestCoroutineDispatcher()
    val listMock = listOf(
        RepositoryModel("teste", 2, 1, User("", "Joao")),
        RepositoryModel("teste2", 3, 3, User("", "Joao Pedro"))
    )
    val githubResponse = GitHubResponse(listMock.size, listMock)
    val githubResponseEmpty = GitHubResponse(0, emptyList())

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}