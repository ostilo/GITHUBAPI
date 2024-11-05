package com.github.githubsearch.data.dto

import com.google.gson.annotations.SerializedName

data class RepositoryDto(
    val id: Long,
    @SerializedName("node_id") val nodeId: String,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val owner: Owner,
    val private: Boolean,
    @SerializedName("html_url") val htmlUrl: String,
    val description: String = "",
    val fork: Boolean,
    val url: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("pushed_at")  val pushedAt: String,
    val homepage: String?,
    val size: Int,
    @SerializedName("stargazers_count")  val stargazersCount: Int,
    @SerializedName("watchers_count")  val watchersCount: Int,
    val language: String= "",
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("open_issues_count")  val openIssuesCount: Int,
    @SerializedName("master_branch") val masterBranch: String?,
    @SerializedName("default_branch") val defaultBranch: String,
    val score: Int,
    val forks: Int,
    @SerializedName("open_issues") val openIssues: Int,
    val watchers: Int = 0,
    @SerializedName("has_issues")val hasIssues: Boolean,
    @SerializedName("has_projects")val hasProjects: Boolean,
    @SerializedName("has_pages")val hasPages: Boolean,
    @SerializedName("has_wiki")val hasWiki: Boolean,
    @SerializedName("has_downloads")val hasDownloads: Boolean,
    val archived: Boolean,
    val disabled: Boolean,
    val visibility: String,
)

data class Owner(
    val login: String,
    val id: Long,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("gravatar_id") val gravatarId: String,
    val url: String,
    @SerializedName("html_url")val htmlUrl: String,
    @SerializedName("followers_url") val followersUrl: String,
    @SerializedName("following_url")val followingUrl: String,
    @SerializedName("gists_url")val gistsUrl: String,
    @SerializedName("starred_url") val starredUrl: String,
    @SerializedName("subscriptions_url") val subscriptionsUrl: String,
    @SerializedName("organizations_url") val organizationUrl: String,
    @SerializedName("repos_url") val reposUrl: String,
    @SerializedName("events_url")val eventsUrl: String,
    @SerializedName("received_events_url")val receivedEventsUrl: String,
    val type: String,
    @SerializedName("has_downloads")val siteAdmin: Boolean
)