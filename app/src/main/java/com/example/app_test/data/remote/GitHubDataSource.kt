package com.example.app_test.data.remote

import com.example.app_test.data.model.GitHubResponse
import com.example.app_test.data.model.Response

interface GitHubDataSource {
    suspend fun getUsers(page: Int): Response<GitHubResponse>
}