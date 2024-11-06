package com.github.githubsearch.data.dto

import com.google.gson.annotations.SerializedName

/* Users RepositoryDto Data Model */
data class RepositoryDto(
    val id: Long,
    @SerializedName("node_id") val nodeId: String,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val owner: Owner,
    val private: Boolean,
    val description: String = "",
    @SerializedName("stargazers_count")  val stargazersCount: Int,
    val language: String= "",
    val watchers: Int = 0,
    val topics : List<String> = arrayListOf()
)

data class Owner(
    val login: String,
    val id: Long,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
)
