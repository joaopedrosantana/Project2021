package com.example.app_test.data.remote.service

import com.example.app_test.data.model.GitHubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GitHubService {

    @GET("search/repositories")
    suspend fun getUsers(
        @Query("q") language: String = "language:kotlin",
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int
    ): Response<GitHubResponse>
}