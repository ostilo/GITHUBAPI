package com.github.githubsearch.ui.screens.mainapp
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.github.githubsearch.R
import com.github.githubsearch.ui.navigation.GithubScreens
import com.github.githubsearch.ui.navigation.Route
import com.github.githubsearch.ui.theme.Dimens
import com.github.githubsearch.ui.theme.PrimaryColor
import com.github.githubsearch.ui.theme.manRopeFontFamily
import org.koin.androidx.compose.koinViewModel

data class TabBarItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: Route
)


@Composable
fun GithubBottomMenu(navController: NavController, sharedViewModel: SharedViewModel = koinViewModel()) {

    // setting up the individual tabs
    val homeTab = TabBarItem(title = stringResource(id = R.string.home), selectedIcon = R.drawable.ic_home_filled, unselectedIcon = R.drawable.ic_home_outlined, route = GithubScreens.HomeScreen)
    val repositoriesTab = TabBarItem(title = stringResource(id = R.string.repositories), selectedIcon = R.drawable.ic_search_filled, unselectedIcon = R.drawable.ic_search_outlined, route = GithubScreens.RepositoriesScreen )
    val usersTab = TabBarItem(title = stringResource(id = R.string.users), selectedIcon = R.drawable.ic_user_filled, unselectedIcon = R.drawable.ic_user_outlined, route = GithubScreens.UsersScreen)

    // creating a list of all the tabs
    val tabBarItems = listOf(homeTab, repositoriesTab, usersTab)

    val shared = sharedViewModel.sharedState
    val selectedTabIndex = shared.selectedTabIndex.collectAsState().value

    NavigationBar(
        containerColor = Color.White,  // or your custom color
        tonalElevation = Dimens.dp5,
        modifier = Modifier.shadow(
            elevation = Dimens.dp10,
            spotColor = Color.Transparent,
            ambientColor = Color.Transparent,
            shape = RectangleShape
        )
    ) {
        // looping over each tab to generate the views and navigation for each item
        CompositionLocalProvider(LocalRippleTheme provides DisabledRippleTheme) {
            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        //selectedTabIndex = index
                        sharedViewModel.updateTabIndex(index)
                        navController.navigate(tabBarItem.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    icon = {
                        TabBarIconView(
                            isSelected = selectedTabIndex == index,
                            selectedIcon = tabBarItem.selectedIcon,
                            unselectedIcon = tabBarItem.unselectedIcon,
                            title = tabBarItem.title,
                        )
                    },
                    label = {
                        Text(
                            tabBarItem.title,
                            textAlign = TextAlign.Start,
                            style = TextStyle(
                                color = PrimaryColor,
                                fontSize =  Dimens.sp10,
                                fontFamily = manRopeFontFamily,
                                fontWeight = if(selectedTabIndex == index) FontWeight.W600 else FontWeight.W400))
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.White,  // Removes the selection background
                    ),
                    interactionSource = remember { MutableInteractionSource() },  // Add this
                    modifier = Modifier.indication(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null  // This removes the ripple
                    )
                )
            }
        }


    }

}

private object DisabledRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = Color.White

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0f, 0f, 0f, 0f)
}


// This component helps to clean up the API call from our TabView above,
@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: Int,
    unselectedIcon: Int,
    title: String,
) {
    Icon(
        painter = if (isSelected) {   painterResource(id = selectedIcon)} else { painterResource(id = unselectedIcon)},
        contentDescription = title,
        modifier = Modifier.offset(y = Dimens.dp8)  // Move icon down
    )
}
