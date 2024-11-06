package com.github.githubsearch.core

import com.github.githubsearch.domain.model.Users

sealed class UserDetailsResult {
    data class Success(val details: Map<String, Users>) : UserDetailsResult()
    data class Error(val message: String) : UserDetailsResult()
}