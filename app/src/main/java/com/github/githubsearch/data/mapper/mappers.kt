package com.github.githubsearch.data.mapper

import com.github.githubsearch.data.dto.RepositoryDto
import com.github.githubsearch.data.dto.UsersDto
import com.github.githubsearch.data.dto.UsersRepoDto
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Repository
import com.github.githubsearch.domain.model.Users
import com.github.githubsearch.domain.model.UsersRepo

/* Clean Architecture, Dto to Model Mapper */
fun GitHubSearchResponse<RepositoryDto>.toDomain() = GitHubSearchResponse(items = this.items.map { it.toDomains() }, totalCount = this.totalCount, incompleteResults = this.incompleteResults)

fun RepositoryDto.toDomains() = Repository(
    id = id,
    description = description,
    ownerAvatarUrl = owner.avatarUrl,
    repoName = fullName,
    starred = stargazersCount.toString(),
    languageName = language,
    issuesDisplayed = openIssues.toString(),
    topics = topics
)

fun GitHubSearchResponse<UsersDto>.toUserDomain() = GitHubSearchResponse(items = this.items.map { it.toUserDomains() }, totalCount = this.totalCount, incompleteResults = this.incompleteResults)

fun UsersDto.toUserDomains() = Users(
  name = name.toString(),
  avatarUrl = avatarUrl.toString(),
  followers = followers.toString(),
  following = following.toString(),
  location = location,
  publicRepos = publicRepos.toString(),
  siteUrl = blog,
  username = login.toString(),
  bio = bio,
  email = email
)


fun List<UsersRepoDto>.toUserRepoDomain() = this.map { it.toUserRepoDomains() }

fun UsersRepoDto.toUserRepoDomains() = UsersRepo(
    fullName = fullName,
    visibility = visibility,
    description = description,
    updatedDate = updatedAt,
    startedCount = stargazersCount,
    language = language,
    forked = fork,
    login = owner.login,
    nodeId = nodeId
)