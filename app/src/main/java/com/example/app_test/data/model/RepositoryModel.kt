package com.example.app_test.data.model

import com.google.gson.annotations.SerializedName

data class GitHubResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val items: List<RepositoryModel>
)

data class RepositoryModel(
    @SerializedName("name") val name: String,
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("forks_count") val forks: Int,
    @SerializedName("owner") val user: User
)

data class User(
    @SerializedName("avatar_url") val photo: String,
    @SerializedName("login") val name: String
)