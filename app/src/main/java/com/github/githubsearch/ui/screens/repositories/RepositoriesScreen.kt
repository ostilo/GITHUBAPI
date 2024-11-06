package com.github.githubsearch.ui.screens.repositories

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.githubsearch.AdvancedNetworkImage
import com.github.githubsearch.DefaultSearchStateContent
import com.github.githubsearch.GithubSearchHeader
import com.github.githubsearch.LoaderScreen
import com.github.githubsearch.R
import com.github.githubsearch.domain.model.GitHubSearchResponse
import com.github.githubsearch.domain.model.Repository
import com.github.githubsearch.ui.theme.BluePillSurface
import com.github.githubsearch.ui.theme.CardStroke
import com.github.githubsearch.ui.theme.Dimens
import com.github.githubsearch.ui.theme.PrimaryColor
import com.github.githubsearch.ui.theme.TextBluePill
import com.github.githubsearch.ui.theme.manRopeFontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepositoriesScreen(viewModel: RepositoriesViewModel = koinViewModel()) {

    val uiState by viewModel.repoState.collectAsState()

    GithubSearchHeader(
        title = R.string.repositories,
        placeHolder = R.string.repo_place_holder,
        screen = { RepositoriesContent(uiState = uiState, viewModel) },
        onSearchClicked = { searchValue ->
            viewModel.fetchAllRepositories(searchCriteria = searchValue)
    })
}

@Composable
fun RepositoriesContent(uiState :  RepositoryScreenState, viewModel: RepositoriesViewModel){
    when(uiState) {
        is RepositoryScreenState.Default -> DefaultSearchStateContent(title = stringResource(id = R.string.search_repo_message))
        is RepositoryScreenState.Loading ->  LoaderScreen(title = R.string.searching_repo_message)
        is RepositoryScreenState.Success ->
            if(uiState.listOfRepos.items.isEmpty()) DefaultSearchStateContent(title = stringResource(id = R.string.search_repo_empty_message)) else ReposListSuccess(uiState.listOfRepos, viewModel )
        is RepositoryScreenState.Error -> DefaultSearchStateContent(title = uiState.errorMessage)
    }
}

@Composable
fun ReposListSuccess(listOfRepos: GitHubSearchResponse<Repository>, viewModel: RepositoriesViewModel) {

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ){
        items(
            count = listOfRepos.items.size,
            key = {
                listOfRepos.items[it].id
            }
        ) { index ->
            RepoContent(repo = listOfRepos.items[index])
        }
    }
}

@Composable
fun RepoContent(repo : Repository){
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
                    AdvancedNetworkImage(
                        contentScale = ContentScale.Inside,
                        imageUrl = repo.ownerAvatarUrl.toString(),
                        modifier = Modifier
                            .padding(end = Dimens.dp12)
                            .height(Dimens.dp25)
                            .width(Dimens.dp25)
                            .clip(RoundedCornerShape(Dimens.dp100))  // Add rounded corners
                    )

                    Text(
                        modifier = Modifier.padding(end = Dimens.dp20).widthIn(max = Dimens.dp120),
                        text = repo.repoName.toString(),
                        fontWeight = FontWeight.W600,
                        color = PrimaryColor,
                        fontSize =  Dimens.sp12,
                        fontFamily = manRopeFontFamily,
                    )

                }

                Row(verticalAlignment = Alignment.CenterVertically) {

                    if(repo.starred?.isNotEmpty() == true){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_starred),
                            contentDescription = stringResource(R.string.stars),
                        )
                        Text(
                            text = repo.starred.orEmpty(),
                            modifier = Modifier.padding(start = Dimens.dp2),
                            fontWeight = FontWeight.W400,
                            color = PrimaryColor,
                            fontSize =  Dimens.sp10,
                            fontFamily = manRopeFontFamily,

                            )
                    }

                    if(repo.languageName?.isNotEmpty() == true){
                        Icon(
                            modifier = Modifier.padding(start = Dimens.dp10),
                            painter = painterResource(id = R.drawable.ic_language_point),
                            contentDescription = stringResource(R.string.stars),
                            tint = Color.Unspecified  // This removes the tint
                        )
                        Text(
                            text = repo.languageName.orEmpty(),
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

            // Description
            if(!repo.description.isNullOrEmpty()) Text(
                text = repo.description.toString(),
                modifier = Modifier.padding(vertical = Dimens.dp15),
                fontWeight = FontWeight.W400,
                color = PrimaryColor,
                fontSize =  Dimens.sp12,
                fontFamily = manRopeFontFamily,
            )


            if(repo.topics.isNotEmpty()) LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .height(40.dp)  // Fixed height
                    .nestedScroll(remember {
                        object : NestedScrollConnection {}
                    })
            ) {
                items(repo.topics.take(3).size) { pills ->
                    PillsTag(text = repo.topics[pills])
                }
            }

        }
    }
}

@Composable
fun PillsTag(text: String) {
    Surface(
        modifier = Modifier.padding(end = Dimens.dp5),
        color = BluePillSurface,
        shape = RoundedCornerShape(Dimens.dp10)
    ) {
        Text(
            text = text.replaceFirstChar { it.uppercase() },
            modifier = Modifier.padding(vertical = Dimens.dp8, horizontal = Dimens.dp16),
            color = TextBluePill,
            fontWeight = FontWeight.W600,
            fontSize =  Dimens.sp10,
            fontFamily = manRopeFontFamily,
            textAlign = TextAlign.Center,
            maxLines =  1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
