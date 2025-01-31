package com.davidmerchan.home.presentation.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.presentation.HomeUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DeleteArticleSnackbar(
    uiState: HomeUIState,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    title: String,
    actionTitle: String,
    onUndoDeleteArticle: (id: ArticleId) -> Unit
) {
    val currentOnUndoDeleteArticle by rememberUpdatedState(onUndoDeleteArticle)

    LaunchedEffect(uiState.successDeleted) {
        uiState.successDeleted?.let { articleId ->
            coroutineScope.launch {
                val action = snackBarHostState.showSnackbar(
                    message = title,
                    duration = SnackbarDuration.Short,
                    actionLabel = actionTitle,
                    withDismissAction = false
                )

                when (action) {
                    SnackbarResult.Dismissed -> Unit
                    SnackbarResult.ActionPerformed -> {
                        currentOnUndoDeleteArticle(articleId)
                        snackBarHostState.currentSnackbarData?.dismiss()
                    }
                }
            }
        }
    }
}
