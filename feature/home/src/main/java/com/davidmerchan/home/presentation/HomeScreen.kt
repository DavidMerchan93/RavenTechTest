package com.davidmerchan.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidmerchan.core.network.NetworkConnectionState
import com.davidmerchan.core.network.rememberConnectivityState
import com.davidmerchan.core.utils.Toast
import com.davidmerchan.designsystem.components.ConnectionMessage
import com.davidmerchan.designsystem.components.ErrorScreen
import com.davidmerchan.designsystem.components.LoadingScreen
import com.davidmerchan.home.R
import com.davidmerchan.home.presentation.components.ArticlesContent
import com.davidmerchan.home.presentation.components.DeleteArticleSnackbar
import com.davidmerchan.home.presentation.viewModel.ArticlesViewModel
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewmodel: ArticlesViewModel = hiltViewModel(),
    goToDetail: (title: String, url: String) -> Unit
) {

    val context = LocalContext.current
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val connectionState by rememberConnectivityState()

    val isConnected by remember(connectionState) {
        derivedStateOf { connectionState === NetworkConnectionState.Available }
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()

    DeleteArticleSnackbar(
        uiState = uiState,
        snackBarHostState = snackBarHostState,
        coroutineScope = coroutine,
        title = stringResource(R.string.article_deleted_message),
        actionTitle = stringResource(R.string.article_deleted_undo_button),
        onUndoDeleteArticle = { articleId ->
            viewmodel.handleEvents(
                HomeUIEvents.RestoreArticle(articleId)
            )
        }
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
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

                    uiState.articles.isEmpty() -> {
                        ErrorScreen(error = stringResource(R.string.empty_articles_error))
                    }

                    uiState.errorDeleted != null -> {
                        Toast(context, stringResource(R.string.article_deleted_error_message))
                    }

                    uiState.isError -> { ErrorScreen() }
                }
            }
        }
    }
}