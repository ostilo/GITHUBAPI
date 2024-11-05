package com.github.githubsearch.ui.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.github.githubsearch.R
import com.github.githubsearch.SetColorStatus
import com.github.githubsearch.ui.navigation.GithubScreens
import com.github.githubsearch.ui.theme.PrimaryColor

@Composable
fun GithubSplashScreen(navController: NavHostController) {

    SetColorStatus(isLightStatusBars = false, statusBarColor = PrimaryColor)
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(durationMillis = 1000,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }
            )
        )
        navController.navigate(GithubScreens.MainAppScreen)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PrimaryColor
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_github_white_mark_logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.scale(scale.value/ 2),
        )
    }
}