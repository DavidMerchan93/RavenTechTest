package com.davidmerchan.detail.presentation

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.davidmerchan.designsystem.theme.RavenTechTestTheme
import com.davidmerchan.designsystem.components.LoadingScreen
import com.davidmerchan.designsystem.components.ShareIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    title: String,
    url: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            onBack()
                        },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                },
                actions = {
                    ShareIcon(context = context, url = url)
                }
            )
        }
    ) { innerPaddings ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings),
            factory = { cont ->
                WebView(cont).apply {
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }
                    }
                }
            },
            update = {
                it.loadUrl(url)
            }
        )
        if (isLoading) {
            LoadingScreen()
        }
    }
}

@Preview
@Composable
internal fun ArticleDetailScreenPreview() {
    RavenTechTestTheme {
        ArticleDetailScreen(
            title = "Title",
            url = "https://example.com",
            onBack = {}
        )
    }
}
