package com.github.githubsearch.ui.screens.mainapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedViewModel(
    val sharedState: MainAppViewState
) : ViewModel() {

    var searchCriteria by mutableStateOf("")
        private set

    fun updateTabIndex(index: Int)  {
        sharedState.selectedTabIndex.value = index
    }
}