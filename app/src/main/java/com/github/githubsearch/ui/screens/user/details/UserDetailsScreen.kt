package com.github.githubsearch.ui.screens.user.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.githubsearch.AdvancedNetworkImage
import com.github.githubsearch.DefaultSearchStateContent
import com.github.githubsearch.LoaderScreen
import com.github.githubsearch.R
import com.github.githubsearch.core.Util.getRelativeTimeSpan
import com.github.githubsearch.domain.model.Users
import com.github.githubsearch.domain.model.UsersRepo
import com.github.githubsearch.ui.screens.components.GithubSpacerHeight
import com.github.githubsearch.ui.screens.components.GithubSpacerWidth
import com.github.githubsearch.ui.screens.mainapp.SharedViewModel
import com.github.githubsearch.ui.theme.CardStroke
import com.github.githubsearch.ui.theme.Dimens
import com.github.githubsearch.ui.theme.LineText
import com.github.githubsearch.ui.theme.PillGray
import com.github.githubsearch.ui.theme.PrimaryColor
import com.github.githubsearch.ui.theme.PurpleText
import com.github.githubsearch.ui.theme.manRopeFontFamily
import org.koin.androidx.compose.koinViewModel
import java.text.MessageFormat

/* User Details Screen */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(navController: NavController, sharedViewModel : SharedViewModel = koinViewModel(), viewModel : UserDetailsViewModel = koinViewModel()) {

    val shared = sharedViewModel.sharedState
    val user = shared.user.collectAsState().value
    val userDetails = shared.userDetails.collectAsState().value

    val uiState by viewModel.userState.collectAsState()

    LaunchedEffect(key1 = true) {
       viewModel.fetchAllUserRepos(user?.username.orEmpty())
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(38.dp),
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = stringResource(R.string.app_name),
                        )
                    }

                    Text(
                        fontWeight = FontWeight.W700,
                        color = PrimaryColor,
                        fontSize =  Dimens.sp18,
                        fontFamily = manRopeFontFamily,
                        text = stringResource(id = R.string.users),
                        modifier = Modifier
                            .padding(start = Dimens.dp10, end = Dimens.dp30)
                    )
                }
            },
            actions = {},
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ),
        )
    }) { paddingValue ->
        UserDetailsContent(paddingValue, user, userDetails, uiState)
    }

}

@Composable
fun UserDetailsContent(paddingValue : PaddingValues, user: Users?, details: Users?, uiState: UserDetailsScreenState) {

    Column(
        modifier = Modifier
            .padding(
                top = Dimens.dp16,
                start = Dimens.dp25,
                end = Dimens.dp16,
                bottom = Dimens.dp10
            )
    ) {

        UsersHeaderContent(details, user)

        GithubSpacerHeight(dimen = Dimens.dp20)

        when(uiState) {
            is UserDetailsScreenState.Loading ->  LoaderScreen(title = R.string.searching_repo_message)
            is UserDetailsScreenState.Success ->
                if(uiState.listOfUserRepos.isEmpty()) DefaultSearchStateContent(title = stringResource(id = R.string.search_user_repo_empty_msg), iconId = R.drawable.ic_empty_icon)
                else UserRepoListSuccess(uiState.listOfUserRepos)
            is UserDetailsScreenState.Error -> DefaultSearchStateContent(title = uiState.errorMessage)
        }
    }
}

@Composable
fun UserRepoListSuccess(listOfRepos: List<UsersRepo>){

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ){
        items(
            count = listOfRepos.size,
            key = {
                listOfRepos[it].nodeId.toString()
            }
        ) { index ->
            RepoContent(repo = listOfRepos[index])
        }
    }

}

@Composable
fun RepoContent(repo: UsersRepo){
    val nameParts = repo.fullName?.split("/")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.dp8)
            .border(
                width = Dimens.dp04,
                color = CardStroke.copy(alpha = 0.6f),
                shape = RectangleShape
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
                    bottom = Dimens.dp10
                )
        ) {
            // Header with repo name and stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.widthIn(max = Dimens.dp150),
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = PurpleText
                                )
                            ) {
                                append("${nameParts?.get(0).orEmpty()}/")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = PrimaryColor
                                )
                            ) {
                                append(nameParts?.get(1))
                            }
                        },
                        style = TextStyle(
                            fontWeight = FontWeight.W600,
                            fontSize =  Dimens.sp12,
                            fontFamily = manRopeFontFamily,
                        ),
                        overflow = TextOverflow.Ellipsis
                    )

                    Surface(
                        color = Color.White,
                        modifier = Modifier.padding(start = Dimens.dp8)
                            .border(
                                width = Dimens.dp1,
                                color = CardStroke,
                                shape = RoundedCornerShape(Dimens.dp6)
                            )
                    ) {
                        Text(
                            text = repo.visibility.orEmpty(),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = TextStyle(
                                color = PrimaryColor,
                                fontWeight = FontWeight.W400,
                                fontSize = Dimens.sp8,
                                fontFamily = manRopeFontFamily,
                            )
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(repo.startedCount?.isNotEmpty() == true){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_starred),
                            contentDescription = stringResource(R.string.stars),
                        )
                        Text(
                            text = repo.startedCount,
                            modifier = Modifier.padding(start = Dimens.dp2),
                            fontWeight = FontWeight.W400,
                            color = PrimaryColor,
                            fontSize =  Dimens.sp10,
                            fontFamily = manRopeFontFamily,

                            )
                    }

                    if(repo.language?.isNotEmpty() == true){
                        Icon(
                            modifier = Modifier.padding(start = Dimens.dp10),
                            painter = painterResource(id = R.drawable.ic_language_point),
                            contentDescription = stringResource(R.string.stars),
                            tint = Color.Unspecified  // This removes the tint
                        )
                        Text(
                            text = repo.language,
                            modifier = Modifier.padding(start = Dimens.dp2),
                            fontWeight = FontWeight.W400,
                            color = PrimaryColor,
                            fontSize =  Dimens.sp10,
                            fontFamily = manRopeFontFamily,
                            overflow = TextOverflow.Clip
                        )
                    }
                }
            }

            if(repo.description?.isNotEmpty() == true){
                Text(
                    text = repo.description,
                    modifier = Modifier.padding(top = Dimens.dp5),
                    fontWeight = FontWeight.W400,
                    color = PrimaryColor,
                    fontSize =  Dimens.sp10,
                    fontFamily = manRopeFontFamily
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth().padding(top = Dimens.dp5),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = if(repo.forked == true) stringResource(R.string.forked) else stringResource(R.string.not_forked),
                    fontWeight = FontWeight.W400,
                    color = LineText,
                    fontSize =  Dimens.sp10,
                    fontFamily = manRopeFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if(repo.updatedDate != null){
                    Text(
                        modifier = Modifier
                            .padding(start = Dimens.dp20, end = Dimens.dp20),
                        text = getRelativeTimeSpan(repo.updatedDate),
                        fontWeight = FontWeight.W600,
                        color = LineText,
                        fontSize =  Dimens.sp12,
                        fontFamily = manRopeFontFamily,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}



@Composable
private fun UsersHeaderContent(
    details: Users?,
    user: Users?
) {
    GithubSpacerHeight(dimen = Dimens.dp50)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {

        AdvancedNetworkImage(
            contentScale = ContentScale.Inside,
            imageUrl = user?.avatarUrl.toString(),
            modifier = Modifier
                .height(Dimens.dp50)
                .width(Dimens.dp50)
                .clip(RoundedCornerShape(Dimens.dp100))  // Add rounded corners
        )

        Column(
            modifier = Modifier.padding(start = Dimens.dp10, top = Dimens.dp5, end = Dimens.dp20),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = details?.name.orEmpty(),
                fontWeight = FontWeight.W600,
                color = PrimaryColor,
                fontSize = Dimens.sp16,
                fontFamily = manRopeFontFamily,
            )

            Text(
                text = user?.username.orEmpty(),
                fontWeight = FontWeight.W500,
                color = PrimaryColor,
                fontSize = Dimens.sp14,
                fontFamily = manRopeFontFamily
            )
        }
    }


    if (details?.bio?.isNotEmpty() == true) Text(
        text = details.bio,
        modifier = Modifier.padding(
            top = Dimens.dp20
        ),
        fontWeight = FontWeight.W400,
        color = PrimaryColor,
        fontSize = Dimens.sp12,
        fontFamily = manRopeFontFamily
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dimens.dp10, top = Dimens.dp10),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {

        if(details?.location != null){
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = stringResource(id = R.string.app_name),
            )

            GithubSpacerWidth(dimen = Dimens.dp5)

            Text(
                text = details.location,
                fontWeight = FontWeight.W500,
                color = PrimaryColor,
                fontSize = Dimens.sp10,
                fontFamily = manRopeFontFamily,
            )

            GithubSpacerWidth(dimen = Dimens.dp5)
        }

        if(details?.siteUrl?.isNotEmpty() == true){
            Icon(
                modifier = Modifier.padding(start = Dimens.dp20),
                painter = painterResource(id = R.drawable.ic_blog_line),
                contentDescription = stringResource(id = R.string.app_name),
            )
            GithubSpacerWidth(dimen = Dimens.dp5)
            Text(
                text = details.siteUrl,
                fontWeight = FontWeight.W600,
                color = PrimaryColor,
                fontSize = Dimens.sp10,
                fontFamily = manRopeFontFamily,
            )
        }
    }

    GithubSpacerHeight(dimen = Dimens.dp10)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dimens.dp10),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_followers),
            contentDescription = stringResource(id = R.string.app_name),
        )
        GithubSpacerWidth(dimen = Dimens.dp5)

        Text(
            text = MessageFormat.format(
                stringResource(id = R.string.follow_people_msg),
                details?.followers?.toDouble()?.toInt(),
                details?.following?.toDouble()?.toInt()
            ),
            fontWeight = FontWeight.W500,
            color = PrimaryColor,
            fontSize = Dimens.sp10,
            fontFamily = manRopeFontFamily,
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.dp20, bottom = Dimens.dp10, end = Dimens.dp20, start = Dimens.dp10),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(id = R.string.repositories),
            fontWeight = FontWeight.W600,
            color = PrimaryColor,
            fontSize = Dimens.sp10,
            fontFamily = manRopeFontFamily,
        )

        GithubSpacerWidth(dimen = Dimens.dp8)

        Surface(
            modifier = Modifier.padding(start = Dimens.dp3),
            shape = RoundedCornerShape(Dimens.dp8),
            color = PillGray
        ) {
            Text(
                text = details?.publicRepos?.toDouble()?.toInt().toString(),
                modifier = Modifier.padding(horizontal = Dimens.dp8, vertical = Dimens.dp4),
                style = TextStyle(
                    color = PrimaryColor,
                    fontWeight = FontWeight.W600,
                    fontSize = Dimens.sp8,
                    fontFamily = manRopeFontFamily,
                )
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Darker part (about 30% of width)
        Box(
            modifier = Modifier
                .weight(0.3f)
                .height(Dimens.dp1)
                .background(PrimaryColor)
        )
        // Lighter part (remaining width)
        Box(
            modifier = Modifier
                .weight(0.7f)
                .height(Dimens.dp1)
                .background(PillGray)
        )
    }
}
