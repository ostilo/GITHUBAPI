package com.github.githubsearch

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.github.githubsearch.ui.navigation.GithubSearchNavigation
import com.github.githubsearch.ui.screens.components.GithubSpacerHeight
import com.github.githubsearch.ui.theme.Dimens
import com.github.githubsearch.ui.theme.FailedRedText
import com.github.githubsearch.ui.theme.GithubSearchTheme
import com.github.githubsearch.ui.theme.Gray
import com.github.githubsearch.ui.theme.PrimaryColor
import com.github.githubsearch.ui.theme.TextGray
import com.github.githubsearch.ui.theme.manRopeFontFamily

typealias ComposableFun = @Composable () -> Unit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubSearchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GithubSearchingApp()
                }
            }
        }
    }
}

@Composable
fun GithubSearchingApp(){
    val navController = rememberNavController()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        GithubSearchNavigation(navController = navController)
    }
}


@Composable
fun SetColorStatus(isLightStatusBars : Boolean, statusBarColor : Color){
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = statusBarColor.toArgb()
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = isLightStatusBars  // true for dark icons, false for light icons
        }
    }
}


@Composable
fun GithubSearchHeader(title : Int, placeHolder : Int, screen: ComposableFun, onSearchClicked: (String) -> Unit){

    var searchText by remember { mutableStateOf(TextFieldValue()) }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Dimens.dp20, top = Dimens.dp25, end = Dimens.dp20)
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = title),
            fontWeight = FontWeight.W700,
            style = TextStyle(
                color = PrimaryColor,
                fontSize =  Dimens.sp18,
                fontFamily = manRopeFontFamily,
            )
        )

        GithubSpacerHeight(dimen = Dimens.dp20)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = Dimens.dp05,
                    color = PrimaryColor,
                    shape = RoundedCornerShape(Dimens.dp5)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_outlined),
                contentDescription = stringResource(id = R.string.search),
                modifier = Modifier.padding(start = Dimens.dp16),
                tint = Gray
            )

            TextField(
                value = searchText, // Replace with your state
                onValueChange = {
                  /* Handle value change */
                    searchText = it
                    if (it.text.isNotEmpty()){
                        isError = false
                    }
                  },
                placeholder = { Text(stringResource(id = placeHolder),
                    fontWeight = FontWeight.W500,
                    style = TextStyle(
                        color = Gray,
                        fontSize =  Dimens.sp10,
                        fontFamily = manRopeFontFamily,
                    )) },
                modifier = Modifier
                    .weight(1f)
                    .border(width = Dimens.dp0, color = Color.Transparent),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryColor,
                ),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            Button(
                onClick = {
                        if(searchText.text.isEmpty()){
                            isError = true
                        }else{
                            onSearchClicked(searchText.text)
                        }
                     },
                modifier = Modifier
                    .padding(Dimens.dp6)
                    .height(Dimens.dp41),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor
                ),
                shape = RoundedCornerShape(Dimens.dp6)
            ) {
                Text(
                    text = stringResource(id = R.string.search),
                    color = Color.White,
                    fontSize = Dimens.sp10,
                    fontWeight = FontWeight.W600,
                    style = TextStyle(
                        color = PrimaryColor,
                        fontFamily = manRopeFontFamily,
                    ),
                    modifier = Modifier.padding(horizontal = Dimens.dp16)
                )
            }
        }

        GithubSpacerHeight(dimen = Dimens.dp5)
        if (isError){
            Text(
                text = stringResource(id = R.string.search_empty_message),
                fontWeight = FontWeight.Normal,
                style = TextStyle(
                    color = FailedRedText,
                    fontSize =  Dimens.sp10,
                    fontFamily = manRopeFontFamily,
                )
            )
        }

        GithubSpacerHeight(dimen = Dimens.dp20)
        screen()

    }
}

// Empty State

@Composable
fun DefaultSearchStateContent(title:Int){

    Column {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search_place_holder),
                    contentDescription = stringResource(id = R.string.app_name),
                )
                
                GithubSpacerHeight(dimen = Dimens.dp20)

                Text(
                    text = stringResource(id = title),
                    fontWeight = FontWeight.W500,
                    style = TextStyle(
                        color = TextGray,
                        fontSize =  Dimens.sp12,
                        fontFamily = manRopeFontFamily,
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

}


