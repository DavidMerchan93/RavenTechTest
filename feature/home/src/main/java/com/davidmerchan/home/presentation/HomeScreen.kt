package com.davidmerchan.home.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidmerchan.designsystem.components.ErrorScreen
import com.davidmerchan.designsystem.components.LoadingScreen
import com.davidmerchan.home.presentation.components.ArticlesContent
import com.davidmerchan.home.presentation.viewModel.ArticlesViewModel
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewmodel: ArticlesViewModel = hiltViewModel(),
    goToDetail: () -> Unit
) {

    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        when {
            uiState.isLoading -> {
                LoadingScreen()
            }

            uiState.articles.isNotEmpty() -> {
                ArticlesContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    articles = uiState.articles.toImmutableList(),
                    onDeleteArticle = {},
                    onGoToDetail = { _, _ ->
                        goToDetail()
                    }
                )
            }

            uiState.isError -> {
                ErrorScreen()
            }
        }
    }
}