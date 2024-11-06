package com.github.githubsearch.data

import com.github.githubsearch.data.dto.Owner
import com.github.githubsearch.data.dto.RepositoryDto
import com.github.githubsearch.data.dto.UsersDto
import com.github.githubsearch.data.dto.UsersRepoDto


object MockRepositoryFactory {
    fun createMockOwner() =  Owner(
        login = "ostilo",
        id  = 23095877,
        nodeId = "MDQ6VXNlcjIzMDk1ODc3",
        avatarUrl = "https://avatars.githubusercontent.com/u/23095877?v=4"
    )

    fun createMockRepositoryDto() = RepositoryDto(
        id = 82128465,
        description = "The most complete and advanced IT security professional toolkit on Android.",
        nodeId = "MDEwOlJlcG9zaXRvcnkyNDc4MjkwMg==",
        name = "open-Android",
        fullName = "open-android/Android",
        owner = createMockOwner(),
        private = false,
        stargazersCount = 14421,
        language = "Kotlin",
        openIssues = 14421,
        topics = listOf("android", "java", "kotlin")
    )

    fun createMockUserRepositoryDto() = UsersRepoDto(
        language = "Kotlin",
        avatarUrl = "https://avatars.githubusercontent.com/u/55284948?v=4",
        fullName = "ostilo/Yahwey",
        description = "Samples for Android Architecture Components.",
        stargazersCount = "200",
        visibility = "Public",
        updatedAt = "2021-09-20T12:16:11Z", email = "olalekanayodeji79@gmail.com", bio = "An experienced android engineer, with a strong passion for innovation", followers = 100.0,
        following = 23.0,
        publicRepos = 503.0,
        fork = false,
        owner = createMockOwner(),
        nodeId = "MDEwOlJlcG9zaXRvcnk0MDg0MzAzNTg="
    )

    fun createMockUserDto() = UsersDto(
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



}