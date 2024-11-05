package com.github.githubsearch.ui.screens.mainapp

import kotlinx.coroutines.flow.MutableStateFlow

class MainAppViewState {
    val selectedTabIndex = MutableStateFlow<Int?>(0)
}