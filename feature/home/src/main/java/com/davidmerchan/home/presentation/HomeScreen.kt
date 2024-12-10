package com.davidmerchan.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davidmerchan.home.presentation.components.ArticlesContent
import kotlinx.collections.immutable.immutableListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, goToDetail: () -> Unit) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Home") }) }
    ) {
        ArticlesContent(
            modifier = Modifier.padding(it),
            articles = immutableListOf(),
            onDeleteArticle = {},
            onGoToDetail = { _, _ -> }
        )
    }
}