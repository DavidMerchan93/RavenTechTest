package com.davidmerchan.home.presentation

import com.davidmerchan.home.domain.model.ArticleId

sealed interface HomeUIEvents {
    data object LoadArticles : HomeUIEvents
    data object ReLoadArticles : HomeUIEvents
    data class DeleteArticle(val id: ArticleId) : HomeUIEvents
    data class RestoreArticle(val id: ArticleId) : HomeUIEvents
}
