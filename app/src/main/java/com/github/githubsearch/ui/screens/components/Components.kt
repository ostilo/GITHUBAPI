package com.github.githubsearch.ui.screens.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp


/* UI component Utils */
@Composable
fun GithubSpacerHeight(dimen: Dp) {
    Spacer(modifier = Modifier.heightIn(dimen))
}

@Composable
fun GithubSpacerWidth(dimen: Dp) {
    Spacer(modifier = Modifier.widthIn(dimen))
}