package com.github.githubsearch.domain.model

data class Repository(
    val id: Long,
    val repoName: String?,
    val ownerAvatarUrl: String?,
    val description: String?,
    val keywords: String?,
    val starred: String?,
    val watchers: String?,
    val languageName: String?,
)
