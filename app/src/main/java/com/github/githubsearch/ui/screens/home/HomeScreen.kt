package com.github.githubsearch.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.github.githubsearch.R
import com.github.githubsearch.domain.model.Users
import com.github.githubsearch.ui.navigation.GithubScreens
import com.github.githubsearch.ui.screens.mainapp.SharedViewModel
import com.github.githubsearch.ui.theme.CardStroke
import com.github.githubsearch.ui.theme.Dimens
import com.github.githubsearch.ui.theme.PrimaryColor
import com.github.githubsearch.ui.theme.RepoCardColor
import com.github.githubsearch.ui.theme.UserCardColor
import com.github.githubsearch.ui.theme.manRopeFontFamily
import org.koin.androidx.compose.koinViewModel

/* Home Screen */
@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Dimens.dp20, top = Dimens.dp25, end = Dimens.dp20)
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.home),
            fontSize = Dimens.sp18,
            fontWeight = FontWeight.W700,
            style = TextStyle(
                color = PrimaryColor,
                fontSize =  Dimens.sp18,
                fontFamily = manRopeFontFamily,
            )
        )
        HomeContainerCard(navController = navController)
    }
}


@Composable
fun HomeContainerCard(navController: NavHostController, sharedViewModel: SharedViewModel = koinViewModel()){

    Row(
        modifier = Modifier
            .padding(top = Dimens.dp30)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Card(
            modifier = Modifier
                .padding(end = Dimens.dp5)
                .height(Dimens.dp130)
                .weight(1f)
                .border(
                    width = Dimens.dp04,
                    color = CardStroke.copy(alpha = 0.6f),
                    shape = RectangleShape
                )
                .clickable {
                    sharedViewModel.updateTabIndex(2)
                    navController.navigate(GithubScreens.UsersScreen)
                }
            ,
            colors = CardDefaults.cardColors(
                containerColor = UserCardColor
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = Dimens.dp0
            ),
            shape = RectangleShape
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = Dimens.dp16,
                        start = Dimens.dp16,
                        end = Dimens.dp16,
                        bottom = Dimens.dp8
                    )
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user_outlined),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .size(Dimens.dp35)
                        .background(Color.White, RectangleShape)
                        .padding(Dimens.dp8)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.users),
                    fontWeight = FontWeight.W700,
                    color = PrimaryColor,
                    fontSize =  Dimens.sp16,
                    fontFamily = manRopeFontFamily,
                )
            }
        }

        Card(
            modifier = Modifier
                .padding(start = Dimens.dp5)
                .height(Dimens.dp130)
                .weight(1f)
                .border(
                    width = Dimens.dp04,
                    color = CardStroke.copy(alpha = 0.6f),
                    shape = RectangleShape
                ).clickable {
                    sharedViewModel.updateTabIndex(1)
                    navController.navigate(GithubScreens.RepositoriesScreen)
                }
            ,
            colors = CardDefaults.cardColors(
                containerColor = RepoCardColor
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = Dimens.dp0
            ),
            shape = RectangleShape
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = Dimens.dp16,
                        start = Dimens.dp16,
                        end = Dimens.dp16,
                        bottom = Dimens.dp8
                    )
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_repository_outlined),
                    contentDescription = "Users",
                    modifier = Modifier
                        .size(Dimens.dp35)
                        .background(Color.White, RectangleShape)
                        .padding(Dimens.dp8)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.repositories),
                    fontWeight = FontWeight.W700,
                    color = PrimaryColor,
                    fontSize =  Dimens.sp16,
                    fontFamily = manRopeFontFamily,
                )
            }
        }
    }

}