package com.davidmerchan.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidmerchan.core.network.NetworkConnectionState
import com.davidmerchan.core.network.rememberConnectivityState
import com.davidmerchan.designsystem.components.ConnectionMessage
import com.davidmerchan.designsystem.components.ErrorScreen
import com.davidmerchan.designsystem.components.LoadingScreen
import com.davidmerchan.home.presentation.components.ArticlesContent
import com.davidmerchan.home.presentation.viewModel.ArticlesViewModel
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewmodel: ArticlesViewModel = hiltViewModel(),
    goToDetail: (title: String, url: String) -> Unit
) {

    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val connectionState by rememberConnectivityState()

    val isConnected by remember(connectionState) {
        derivedStateOf { connectionState === NetworkConnectionState.Available }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->

        PullToRefreshBox(
            modifier = Modifier.padding(contentPadding),
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewmodel.handleEvents(HomeUIEvents.LoadArticles) }
        ) {
            Column(
                modifier = Modifier.padding(contentPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isConnected.not()) {
                    ConnectionMessage()
                }
                when {
                    uiState.isLoading -> {
                        LoadingScreen()
                    }

                    uiState.articles.isNotEmpty() -> {
                        ArticlesContent(
                            modifier = Modifier
                                .fillMaxSize(),
                            articles = uiState.articles.toImmutableList(),
                            onDeleteArticle = { id ->
                                viewmodel.handleEvents(HomeUIEvents.DeleteArticle(id))
                            },
                            onGoToDetail = { title, url ->
                                goToDetail(title, url)
                            }
                        )
                    }

                    uiState.isError -> {
                        ErrorScreen()
                    }
                }
            }
        }
    }
}