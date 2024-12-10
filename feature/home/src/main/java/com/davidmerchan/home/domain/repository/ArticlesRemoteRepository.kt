package com.davidmerchan.home.domain.repository

import com.davidmerchan.home.domain.model.ArticleModel

interface ArticlesRemoteRepository {
    suspend fun getArticles(): Result<List<ArticleModel>>
}
