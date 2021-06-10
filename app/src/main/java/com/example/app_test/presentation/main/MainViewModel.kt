package com.example.app_test.presentation.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.example.app_test.data.model.ErrorResponse
import com.example.app_test.data.model.RepositoryModel
import com.example.app_test.data.model.SuccessResponse
import com.example.app_test.data.repository.GitHubRepository
import com.example.app_test.presentation.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: GitHubRepository) : BaseViewModel() {
    val loading: LiveData<Boolean> get() = _loading
    val updateList: LiveData<List<RepositoryModel>> get() = _updateList
    val error: LiveData<String> get() = _error

    var page = 0

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _updateList: MutableLiveData<List<RepositoryModel>> = MutableLiveData()
    private val _error: MutableLiveData<String> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun fetchRepositories() {
        _loading.postValue(true)
        page++
        if (page > 5) {
            _loading.postValue(false)
            return
        }
        uiScope.launch {
            when (val response = repository.getRepositories(page)) {
                is SuccessResponse -> _updateList.postValue(response.body.items)
                is ErrorResponse -> _error.postValue(response.error.message)
            }
            _loading.postValue(false)
        }
    }
}