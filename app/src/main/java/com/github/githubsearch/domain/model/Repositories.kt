package com.github.githubsearch.domain.model

/* Repository Data Model */
data class Repository(
    val id: Long,
    val repoName: String?,
    val ownerAvatarUrl: String?,
    val description: String?,
    val starred: String?,
    val watchers: String?,
    val languageName: String?,
   val topics : List<String>
)
