package com.github.githubsearch.data.mapper

import com.github.githubsearch.data.dto.RepositoryDto
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Repository


fun GitHubSearchResponse<RepositoryDto>.toDomain() = GitHubSearchResponse(items = this.items.map { it.toDomains() }, totalCount = this.totalCount, incompleteResults = this.incompleteResults)

fun RepositoryDto.toDomains() = Repository(
    id = id,
    description = description,
    ownerAvatarUrl = owner.avatarUrl,
    repoName = fullName,
    starred = stargazersCount.toString(),
    languageName = language,
    watchers = watchers.toString(),
    keywords = ""

)