package com.example.app_test

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.example.app_test.RecyclerViewMatchers.checkRecyclerViewItem
import com.example.app_test.data.model.GitHubResponse
import com.example.app_test.data.model.RepositoryModel
import com.example.app_test.data.model.User
import com.example.app_test.presentation.main.MainActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit


class MainActivityTest {

    private val server = MockWebServer()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        server.start(serverPort)
    }

    @After
    fun tearDown() {
        server.close()
    }

    @Test
    fun shouldDisplayTitle() {
        mockSuccessResponse()
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItem() {
        mockSuccessResponse()
        launchActivity<MainActivity>().apply {
            Thread.sleep(1000)
            checkRecyclerViewItem(R.id.recyclerView, 0, withText("teste"))
        }

    }

    private fun configCall(responseJson: String, code: Int) {
        val mockResponse = MockResponse()
            .setResponseCode(code)
            .setBody(responseJson)
            .setBodyDelay(1, TimeUnit.MILLISECONDS)
        server.enqueue(mockResponse)
    }

    private fun mockSuccessResponse() {
        configCall(Gson().toJson(githubResponse), HttpURLConnection.HTTP_OK)
    }

    private fun mockErrorResponse() {
        configCall("{\"status\":1, \"message\":\"Error\"}", HttpURLConnection.HTTP_BAD_REQUEST)
    }

    companion object {
        private const val serverPort = 8081
        val listMock = listOf(
            RepositoryModel("teste", 2, 1, User("-", "Joao")),
            RepositoryModel("teste2", 3, 3, User("-", "Joao Pedro"))
        )
        val githubResponse = GitHubResponse(listMock.size, listMock)
    }
}