package com.github.githubsearch.domain.model

import com.google.gson.annotations.SerializedName

/* Generic Base Response Model */
data class GitHubSearchResponse<T>(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results")  val incompleteResults: Boolean,
    val items: List<T>
)