package com.example.app_test.data.remote

import com.example.app_test.data.model.GitHubResponse
import com.example.app_test.data.model.Response
import com.example.app_test.data.remote.service.GitHubService

class GitHubDataSourceImpl(
    private val service: GitHubService
) : GitHubDataSource {

    override suspend fun getUsers(page:Int): Response<GitHubResponse> {
        return service.getUsers(page = page).formatResponse()
    }
}