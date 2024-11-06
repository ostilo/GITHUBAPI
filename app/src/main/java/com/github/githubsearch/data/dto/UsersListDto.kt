package com.github.githubsearch.data.dto

import com.google.gson.annotations.SerializedName

/* Users UsersDto Data Model */

data class UsersDto(
    val login : String? = null,
    @SerializedName("avatar_url") val avatarUrl : String? = null,
    @SerializedName("html_url") val htmlUrl : String? = null,
    val name : String? = null,
    val blog : String? = null,
    val location : String? = null,
    val email : String? = null,
    val bio : String? = null,
    val followers : Double? = null,
    val following : Double? = null,
    @SerializedName("public_repos") val publicRepos : Double? = null
)