package com.github.githubsearch.ui.screens.mainapp

import com.github.githubsearch.domain.model.Users
import kotlinx.coroutines.flow.MutableStateFlow

class MainAppViewState {
    val selectedTabIndex = MutableStateFlow<Int?>(0)
    val user = MutableStateFlow<Users?>(null)
    val userDetails = MutableStateFlow<Users?>(null)
}