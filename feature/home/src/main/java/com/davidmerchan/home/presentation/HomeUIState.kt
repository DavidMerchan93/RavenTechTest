package com.davidmerchan.home.presentation

import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.presentation.model.Article

data class HomeUIState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val successDeleted: ArticleId? = null,
    val errorDeleted: ArticleId? = null,
    val isRefreshing: Boolean = false,
    val isError: Boolean = false,
)
