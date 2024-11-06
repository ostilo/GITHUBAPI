package com.github.githubsearch

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.githubsearch.core.Constants.PROGRESS_VALUE
import com.github.githubsearch.ui.navigation.GithubSearchNavigation
import com.github.githubsearch.ui.screens.components.GithubSpacerHeight
import com.github.githubsearch.ui.theme.CardStroke
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
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

/* To update app control color. */
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


/* A reusable composable function for repository and user composable screen */
@Composable
fun GithubSearchHeader(title : Int, placeHolder : Int, screen: ComposableFun, onSearchClicked: (String) -> Unit){

    var searchText by remember { mutableStateOf(TextFieldValue()) }
    var isError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

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
                textStyle = TextStyle(
                    textDecoration = TextDecoration.None,
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Normal
                ),
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
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                maxLines = 1,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
            )

            Button(
                onClick = {
                        if(searchText.text.isEmpty()){
                            isError = true
                        }else{
                            focusManager.clearFocus()
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


/* A reusable composable function to show screen, app ,network state */

@Composable
fun DefaultSearchStateContent(title:String, iconId : Int = R.drawable.ic_search_place_holder){

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
                    painter = painterResource(id = iconId),
                    contentDescription = stringResource(id = R.string.app_name),
                )
                
                GithubSpacerHeight(dimen = Dimens.dp20)

                Text(
                    text = title,
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


/* A loading composable for the app */
@Composable
fun LoaderScreen(title : Int) {
    val infiniteTransition = rememberInfiniteTransition(label = stringResource(id = R.string.app_name))

    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = PROGRESS_VALUE, animationSpec = infiniteRepeatable(animation = tween(900)),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                progress = progressAnimationValue,
                color = PrimaryColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(start = Dimens.dp10),
                text = stringResource(title),
                fontSize = Dimens.sp14,
                color = PrimaryColor,
                fontFamily = manRopeFontFamily,
            )
        }
    }
}



/* An image loader to load network images. */
@Composable
fun AdvancedNetworkImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .memoryCacheKey(imageUrl)  // Cache key
                .diskCacheKey(imageUrl)    // Disk cache key
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = contentScale,
            onLoading = { isLoading = true },
            onSuccess = { isLoading = false },
            onError = {
                isLoading = false
                isError = true
            }
        )

        // Loading shimmer effect
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CardStroke)
            )
        }

        // Error state
        if (isError) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.1f))
            ) {
                Icon(
                    painter= painterResource(id = R.drawable.ic_user_outlined),
                    contentDescription = "Error",
                    modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}


