package com.github.githubsearch.ui.navigation

import kotlinx.serialization.Serializable

/* App Navigation screen names */
sealed class GithubScreens : Route {

    @Serializable
    data object SplashScreen: GithubScreens()

    @Serializable
    data object MainAppScreen: GithubScreens()

    @Serializable
    data object HomeScreen: GithubScreens()

    @Serializable
    data object RepositoriesScreen: GithubScreens()

    @Serializable
    data object UsersScreen: GithubScreens()

    @Serializable
    data object UserDetailsScreen : GithubScreens()

}