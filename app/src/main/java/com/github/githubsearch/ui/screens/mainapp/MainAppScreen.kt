package com.github.githubsearch.ui.screens.mainapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.github.githubsearch.SetColorStatus
import com.github.githubsearch.ui.navigation.GithubScreens
import com.github.githubsearch.ui.navigation.mainAppNavigation

@Composable
fun MainAppScreen() {

    SetColorStatus(isLightStatusBars = true, statusBarColor = Color.White)

    val navController = rememberNavController()
    Scaffold(
        bottomBar = { GithubBottomMenu(navController = navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            NavHost(navController = navController, startDestination = GithubScreens.HomeScreen) {
                mainAppNavigation(navController = navController)
            }
        }
    }
}