package com.davidmerchan.home.presentation

import com.davidmerchan.home.domain.model.ArticleModel

data class HomeUIState(
    val articles: List<ArticleModel> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
