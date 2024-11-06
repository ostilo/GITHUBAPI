package com.github.githubsearch.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.github.githubsearch.ui.screens.home.HomeScreen
import com.github.githubsearch.ui.screens.repositories.RepositoriesScreen
import com.github.githubsearch.ui.screens.user.UsersScreen
import com.github.githubsearch.ui.screens.user.details.UserDetailsScreen

/* Main Dashboard / Home Navigation */
fun NavGraphBuilder.mainAppNavigation(navController: NavHostController) {
    composable<GithubScreens.HomeScreen> {
       HomeScreen(navController = navController)
    }

    composable<GithubScreens.RepositoriesScreen> {
        RepositoriesScreen()
    }

    composable<GithubScreens.UsersScreen> {
        UsersScreen(navController = navController)
    }

    composable<GithubScreens.UserDetailsScreen> {
        UserDetailsScreen(navController = navController)
    }
}