package com.github.githubsearch.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.github.githubsearch.ui.screens.home.HomeScreen
import com.github.githubsearch.ui.screens.repositories.RepositoriesScreen
import com.github.githubsearch.ui.screens.user.UsersScreen

fun NavGraphBuilder.mainAppNavigation(navController: NavHostController) {
    composable<GithubScreens.HomeScreen> {
       HomeScreen(navController = navController)
    }

    composable<GithubScreens.RepositoriesScreen> {
        RepositoriesScreen()
    }

    composable<GithubScreens.UsersScreen> {
        UsersScreen()
    }


}