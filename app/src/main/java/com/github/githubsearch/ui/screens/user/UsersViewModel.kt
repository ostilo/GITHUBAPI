package com.github.githubsearch.ui.screens.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.githubsearch.core.Constants
import com.github.githubsearch.core.GithubExtension.getStatusCode
import com.github.githubsearch.core.GithubExtension.getStatusMessage
import com.github.githubsearch.core.Result
import com.github.githubsearch.core.UserDetailsResult
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Users
import com.github.githubsearch.domain.usecase.GetAllUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(
     private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

     private val _userState = MutableStateFlow<UserScreenState>(UserScreenState.Default)
     val userState: StateFlow<UserScreenState> = _userState.asStateFlow()

     private val _userDetails = mutableStateOf<Map<String, Users>>(emptyMap())
     val userDetails = _userDetails


     fun fetchAllUsers(pageNo : Int = Constants.DEFAULT_PAGE_NUMBER, perPage : Int = Constants.DEFAULT_USER_PAGE_COUNT, searchCriteria : String = "") {
          viewModelScope.launch {
               _userState.update { UserScreenState.Loading }
               val response = getAllUsersUseCase.execute(pageNo = pageNo, perPage = perPage, searchCriteria = searchCriteria)
               handleGetAllUserResponse(response)
          }
     }

     private fun handleGetAllUserResponse(response: Result<GitHubSearchResponse<Users>>) {
          when (response) {
               is Result.Success -> setUserListState(UserScreenState.Success(response.data))
               is Result.Error -> setUserListState(UserScreenState.Error(response.exception, response.exception.getStatusCode().getStatusMessage()))
          }
     }

     private fun setUserListState(state: UserScreenState) {
          _userState.update {
               state
          }
     }

     fun fetchUserDetails(users: List<Users>) {
          viewModelScope.launch {
               when (val result = getUserDetails(users)) {
                    is UserDetailsResult.Success -> {
                         _userDetails.value = result.details
                    }
                    is UserDetailsResult.Error -> {
                         result.message
                    }
               }
          }
     }

     private suspend fun getUserDetails(users: List<Users>): UserDetailsResult {
          return try {
               val results = users.mapNotNull { user ->
                    try {
                         when(val details = getAllUsersUseCase.execute(username = user.username)){
                              is Result.Success -> details.let { user.username to it.data }
                              is Result.Error -> details.let { user.username to it.exception }
                         }
                    } catch (e: Exception) {
                         null
                    }
               }.toMap()
               val emptyMap = mutableMapOf<String, Users>()
               if (results.isNotEmpty()) {
                    results.forEach { (s, any) ->
                         if(any is Users){
                              emptyMap[s] = any
                              UserDetailsResult.Success(emptyMap)
                         }
                    }
                    UserDetailsResult.Success(emptyMap)
               } else {
                    UserDetailsResult.Error("")
               }
          } catch (e: Exception) {
               UserDetailsResult.Error("")
          }
     }

}