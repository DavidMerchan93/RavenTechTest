package com.davidmerchan.home.domain.repository

import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.domain.model.ArticleModel

interface ArticlesLocalRepository {
    suspend fun getAllArticles(): Result<List<ArticleModel>>
    suspend fun saveArticles(articleModel: List<ArticleModel>)
    suspend fun deleteArticle(id: ArticleId)
    suspend fun restoreArticle(id: ArticleId): Result<ArticleModel>
}
