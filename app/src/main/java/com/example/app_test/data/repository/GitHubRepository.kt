package com.example.app_test.data.repository

import com.example.app_test.data.model.GitHubResponse
import com.example.app_test.data.model.Response

interface GitHubRepository {
    suspend fun getRepositories(page: Int): Response<GitHubResponse>
}