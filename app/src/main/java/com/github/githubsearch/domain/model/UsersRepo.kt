package com.github.githubsearch.domain.model

/* Users Repository Data Model */
data class UsersRepo (

    val fullName : String? = null,
    val visibility : String? = null,
    val description : String? = null,
    val updatedDate : String? = null,
    val startedCount : String? = null,
    val language : String? = null,
    val forked : Boolean? = null,
    val login : String? = null,
    val nodeId : String? = null

)