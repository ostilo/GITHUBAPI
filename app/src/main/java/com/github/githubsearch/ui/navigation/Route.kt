package com.github.githubsearch.ui.navigation

import androidx.navigation.NavOptionsBuilder

interface Route

fun NavOptionsBuilder.navOptions(route: Route, include: Boolean = true) {
    popUpTo(route){
        inclusive = include
    }
    launchSingleTop = true
}