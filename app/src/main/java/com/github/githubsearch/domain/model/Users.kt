package com.github.githubsearch.domain.model


/* Users Data Model */
data class Users(
    val name : String,
    val username : String,
    val location : String?,
    val avatarUrl : String,
    val followers : String,
    val following : String,
    val siteUrl : String?,
    val publicRepos : String,
    val bio : String?,
    val email : String?
)