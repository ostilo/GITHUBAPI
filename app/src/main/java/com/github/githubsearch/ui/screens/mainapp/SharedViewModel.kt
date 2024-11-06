package com.github.githubsearch.ui.screens.mainapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.githubsearch.domain.model.Users

/* Shared Viewmodel State for passing data in the MVI pattern */
class SharedViewModel(
    val sharedState: MainAppViewState
) : ViewModel() {

    var searchCriteria by mutableStateOf("")
        private set

    fun updateTabIndex(index: Int)  {
        sharedState.selectedTabIndex.value = index
    }

    fun updateUsersInfo(user: Users, userDetails : Users?)  {
        sharedState.user.value = user
        sharedState.userDetails.value = userDetails
    }

}