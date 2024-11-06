package com.github.githubsearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.githubsearch.ui.screens.mainapp.MainAppScreen
import com.github.githubsearch.ui.screens.splash.GithubSplashScreen

/* Main App / Home  Navigation */
@Composable
fun GithubSearchNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = GithubScreens.SplashScreen) {
        composable<GithubScreens.SplashScreen> {
            GithubSplashScreen(navController = navController)
        }

        composable<GithubScreens.MainAppScreen> {
            MainAppScreen()
        }
    }
}