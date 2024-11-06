package com.github.githubsearch.data.dto

import com.google.gson.annotations.SerializedName
/* Users UsersRepoDto Data Model */

data class UsersRepoDto(
    val language : String? = null,
    @SerializedName("avatar_url") val avatarUrl : String? = null,
    @SerializedName("full_name") val fullName : String? = null,
    val description : String? = null,
    @SerializedName("stargazers_count")  val stargazersCount : String? = null,
    val visibility : String? = null,
    @SerializedName("updated_at") val updatedAt : String? = null,
    val email : String? = null,
    val bio : String? = null,
    val followers : Double? = null,
    val following : Double? = null,
    @SerializedName("public_repos") val publicRepos : Double? = null,
    val fork : Boolean? = null,
    val owner: Owner,
    @SerializedName("node_id") val nodeId : String
)