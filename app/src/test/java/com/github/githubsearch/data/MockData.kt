package com.github.githubsearch.data

import com.github.githubsearch.data.MockRepositoryFactory.createMockOwner
import com.github.githubsearch.data.MockRepositoryFactory.createMockRepositoryDto
import com.github.githubsearch.data.MockRepositoryFactory.createMockUserDto
import com.github.githubsearch.data.MockRepositoryFactory.createMockUserRepositoryDto
import com.github.githubsearch.data.dto.Owner
import com.github.githubsearch.data.dto.RepositoryDto
import com.github.githubsearch.data.dto.UsersDto
import com.github.githubsearch.data.dto.UsersRepoDto
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Repository
import com.github.githubsearch.domain.model.Users
import com.github.githubsearch.domain.model.UsersRepo


val mockRepositoryListDto = GitHubSearchResponse(items = listOf(createMockRepositoryDto()), totalCount = 50, incompleteResults = false)
val mockUsersListDto = GitHubSearchResponse(items = listOf(createMockUserDto()), totalCount = 50, incompleteResults = false)
val mockUsersRepoListDto = listOf(createMockUserRepositoryDto())


val mockRepository = Repository(
    id = 82128465,
    repoName = "open-android/Android",
    ownerAvatarUrl = "https://avatars.githubusercontent.com/u/23095877?v=4",
    description = "The most complete and advanced IT security professional toolkit on Android",
    starred = "14421",
    watchers = "14421",
    languageName = "Kotlin",
    topics = listOf("android", "java", "kotlin")
)

val mockRepositoryDto = RepositoryDto(
        id = 82128465,
        description = "The most complete and advanced IT security professional toolkit on Android",
        nodeId = "MDEwOlJlcG9zaXRvcnkyNDc4MjkwMg==",
        name = "open-Android",
        fullName = "open-android/Android",
        owner = createMockOwner(),
        private = false,
        stargazersCount = 14421,
        language = "Kotlin",
        watchers = 14421,
        topics = listOf("android", "java", "kotlin")
    )


val mockOwner = Owner(
    login = "open-android",
    id  = 23095877,
    nodeId = "MDQ6VXNlcjIzMDk1ODc3",
    avatarUrl = "https://avatars.githubusercontent.com/u/23095877?v=4"
)


val mockUsers = Users(
    name = "Olalekan Taiye Ayodeji",
    username = "ostilo",
    location = "Lagos",
    avatarUrl = "https://avatars.githubusercontent.com/u/55284948?v=4",
    followers = "100.0",
    following = "23.0",
    siteUrl = "https://github.com/ostilo",
    publicRepos = "503.0",
    bio = "An experienced android engineer, with a strong passion for innovation",
    email = "olalekanayodeji79@gmail.com",
)


val mockUsersDto  = UsersDto(
    login = "ostilo",
    avatarUrl = "https://avatars.githubusercontent.com/u/55284948?v=4",
    htmlUrl = "https://github.com/ostilo",
    name = "Olalekan Taiye Ayodeji",
    blog = "https://github.com/ostilo",
    location = "Lagos",
    email = "olalekanayodeji79@gmail.com",
    bio = "An experienced android engineer, with a strong passion for innovation",
    followers = 100.0,
    following = 23.0,
    publicRepos = 503.0
)

val mockUsersRepoDto = UsersRepoDto(
    avatarUrl = "https://avatars.githubusercontent.com/u/55284948?v=4",
    stargazersCount = "343",
    updatedAt = "2021-09-20T12:16:11Z",
    email = "olalekanayodeji79@gmail.com",
    bio = "An experienced android engineer, with a strong passion for innovation", followers = 100.0,
    following = 23.0,
    publicRepos = 503.0,
    fork = false,
    owner = createMockOwner(),
    fullName = "Olalekan Taiye Ayodeji",
    visibility = "Public",
    description = "Samples for Android Architecture Components.",
    language = "Kotlin",
    nodeId = "R_kgDOGIJMIA"
)

val mockUsersRepo = UsersRepo(
    fullName = "Olalekan Taiye Ayodeji",
    visibility = "Public",
    description = "Samples for Android Architecture Components.",
    updatedDate = "2021-09-20T12:16:11Z",
    startedCount = "343",
    language = "Kotlin",
    forked = false,
    login = "ostilo",
    nodeId = "R_kgDOGIJMIA"
)


val mockUsersList = listOf(mockUsers)
val mockUsersRepoList = listOf(createMockUserRepositoryDto())
val mockRepoList = listOf(createMockRepositoryDto())