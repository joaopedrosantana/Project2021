package com.example.app_test.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.example.app_test.BaseTest
import com.example.app_test.data.model.ErrorResponse
import com.example.app_test.data.model.RepositoryModel
import com.example.app_test.data.model.RequestError
import com.example.app_test.data.model.SuccessResponse
import com.example.app_test.data.repository.GitHubRepository
import com.example.app_test.presentation.main.MainViewModel
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseTest() {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mockk<GitHubRepository>()

    private val viewModel = MainViewModel(repository)

    @Test
    fun whenReturnSuccess() {
        coEvery { repository.getRepositories(1) } returns SuccessResponse(githubResponse)
        val mockedObserver: Observer<List<RepositoryModel>> = spyk(Observer { })
        viewModel.updateList.observe(mockLifecycleOwner(), mockedObserver)
        viewModel.fetchRepositories()
        val slot = slot<List<RepositoryModel>>()
        verify { mockedObserver.onChanged(capture(slot)) }
        assertEquals(slot.captured, githubResponse.items)
    }

    @Test
    fun whenReturnError() {
        val requestError = RequestError(0, "Error")
        coEvery { repository.getRepositories(1) } returns ErrorResponse(requestError)
        val mockedObserver: Observer<String> = spyk(Observer { })
        viewModel.error.observe(mockLifecycleOwner(), mockedObserver)
        viewModel.fetchRepositories()
        val slot = slot<String>()
        verify { mockedObserver.onChanged(capture(slot)) }
        assertEquals(slot.captured, requestError.message)
    }


    private fun mockLifecycleOwner(): LifecycleOwner {
        val owner: LifecycleOwner = mockk()
        val lifecycle = LifecycleRegistry(owner)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        every { owner.lifecycle } returns (lifecycle)
        return owner
    }
}
