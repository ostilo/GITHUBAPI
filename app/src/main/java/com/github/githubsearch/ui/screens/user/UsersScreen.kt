package com.github.githubsearch.ui.screens.user

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.github.githubsearch.AdvancedNetworkImage
import com.github.githubsearch.DefaultSearchStateContent
import com.github.githubsearch.GithubSearchHeader
import com.github.githubsearch.LoaderScreen
import com.github.githubsearch.R
import com.github.githubsearch.core.Constants.PROGRESS_VALUE
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Users
import com.github.githubsearch.ui.navigation.GithubScreens
import com.github.githubsearch.ui.screens.mainapp.SharedViewModel
import com.github.githubsearch.ui.theme.CardStroke
import com.github.githubsearch.ui.theme.Dimens
import com.github.githubsearch.ui.theme.PrimaryColor
import com.github.githubsearch.ui.theme.TextBluePill
import com.github.githubsearch.ui.theme.manRopeFontFamily
import org.koin.androidx.compose.koinViewModel

/* User list Screen */
@Composable
fun UsersScreen(navController: NavController, viewModel: UsersViewModel = koinViewModel()) {

    val uiState by viewModel.userState.collectAsState()

    GithubSearchHeader(
        title = R.string.users,
        placeHolder = R.string.user_place_holder,
        screen = { UsersContent(uiState = uiState, viewModel, navController = navController) },
        onSearchClicked = { searchValue ->
            viewModel.fetchAllUsers(searchCriteria = searchValue)
        })
}

@Composable
fun UsersContent(uiState : UserScreenState, viewModel: UsersViewModel, navController: NavController){
    when(uiState) {
        is UserScreenState.Default -> DefaultSearchStateContent(title = stringResource(id = R.string.search_user_message))
        is UserScreenState.Loading ->  LoaderScreen(title = R.string.searching_user_message)
        is UserScreenState.Success ->
            if(uiState.listOfUsers.items.isEmpty()) DefaultSearchStateContent(title = stringResource(id = R.string.search_user_empty_message))
            else UsersListSuccess(uiState.listOfUsers, viewModel, navController )
        is UserScreenState.Error -> DefaultSearchStateContent(title = uiState.errorMessage)
    }
}

@Composable
fun UsersListSuccess(listOfUsers: GitHubSearchResponse<Users>, viewModel: UsersViewModel, navController: NavController, sharedViewModel: SharedViewModel = koinViewModel()) {

    LaunchedEffect(listOfUsers.items) {
        viewModel.fetchUserDetails(listOfUsers.items)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ){
        items(
            count = listOfUsers.items.size,
            key = {
                listOfUsers.items[it].username
            }
        ) { index ->
            // Just use the already fetched details
            val user = listOfUsers.items[index]
            UserContent(user = listOfUsers.items[index], details = viewModel.userDetails.value[user.username]){ userInfo, details ->
                sharedViewModel.updateUsersInfo(userInfo, details)
                navController.navigate(GithubScreens.UserDetailsScreen)
            }
        }
    }
}

@Composable
fun UserContent(user : Users, details: Users?, onCardClicked: (Users,Users?) -> Unit){

    val infiniteTransition = rememberInfiniteTransition(label = stringResource(id = R.string.app_name))
    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = PROGRESS_VALUE, animationSpec = infiniteRepeatable(animation = tween(900)),
        label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.dp8)
            .border(
                width = Dimens.dp04,
                color = CardStroke.copy(alpha = 0.6f),
                shape = RectangleShape
            ).clickable { onCardClicked(user, details) }
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.dp0
        ),
        shape = RectangleShape
    ) {
        // Header with repo name and stats
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                top = Dimens.dp16,
                start = Dimens.dp16,
                end = Dimens.dp16,
                bottom = Dimens.dp10
            ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AdvancedNetworkImage(
                contentScale = ContentScale.Inside,
                imageUrl = user.avatarUrl,
                modifier = Modifier
                    .padding(end = Dimens.dp12)
                    .height(Dimens.dp25)
                    .width(Dimens.dp25)
                    .clip(RoundedCornerShape(Dimens.dp100))  // Add rounded corners
            )

            if(details == null){
                Box(
                    modifier = Modifier
                        .size(Dimens.dp15),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = progressAnimationValue,
                        color = PrimaryColor,
                        strokeWidth = Dimens.dp05
                    )
                }

            }

            Column(
                modifier = Modifier
                    .padding(
                        start = Dimens.dp16,
                        end = Dimens.dp16,
                    )
            ) {

                Text(
                    modifier = Modifier
                        .padding(end = Dimens.dp20),
                    text = details?.name.orEmpty(),
                    fontWeight = FontWeight.W600,
                    color = TextBluePill,
                    fontSize =  Dimens.sp12,
                    fontFamily = manRopeFontFamily,
                )


                Text(
                    text = user.username,
                    fontWeight = FontWeight.W400,
                    color = PrimaryColor,
                    fontSize =  Dimens.sp12,
                    fontFamily = manRopeFontFamily
                )

                if(details?.bio?.isNotEmpty() == true){
                    Text(
                        text = details.bio,
                        modifier = Modifier.padding(top = Dimens.dp5),
                        fontWeight = FontWeight.W400,
                        color = PrimaryColor,
                        fontSize =  Dimens.sp12,
                        fontFamily = manRopeFontFamily
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if(details?.location?.isNotEmpty() == true) Text(
                        modifier = Modifier.widthIn(max = Dimens.dp120),
                        text = details.location,
                        fontWeight = FontWeight.W400,
                        color = PrimaryColor,
                        fontSize =  Dimens.sp10,
                        fontFamily = manRopeFontFamily,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )


                    if(details?.email?.isNotEmpty() == true) Text(
                        modifier = Modifier
                            .padding(end = Dimens.dp20),
                        text = details.email,
                        fontWeight = FontWeight.W400,
                        color = PrimaryColor,
                        fontSize =  Dimens.sp10,
                        fontFamily = manRopeFontFamily,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )


                }
            }


        }
    }
}
