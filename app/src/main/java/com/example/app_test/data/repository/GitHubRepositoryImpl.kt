package com.example.app_test.data.repository

import com.example.app_test.data.model.GitHubResponse
import com.example.app_test.data.model.Response
import com.example.app_test.data.remote.GitHubDataSource

class GitHubRepositoryImpl(
    private val remoteDataSource: GitHubDataSource
) : GitHubRepository {
    override suspend fun getRepositories(page: Int): Response<GitHubResponse> {
        return remoteDataSource.getUsers(page);
    }
}