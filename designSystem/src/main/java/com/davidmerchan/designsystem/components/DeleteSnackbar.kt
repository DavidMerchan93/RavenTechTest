package com.davidmerchan.designsystem.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DeleteSnackbar(
    successDeleted: Boolean?,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    title: String,
    actionTitle: String,
    onUndoDelete: () -> Unit
) {
    val currentOnUndoDeleteArticle by rememberUpdatedState(onUndoDelete)

    LaunchedEffect(successDeleted) {
        successDeleted?.let {
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
                        currentOnUndoDeleteArticle()
                        snackBarHostState.currentSnackbarData?.dismiss()
                    }
                }
            }
        }
    }
}