package com.davidmerchan.home.data.repository

import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository

class ArticlesLocalDatasource: ArticlesLocalRepository {
    override suspend fun getAllArticles(): Result<List<ArticleModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveArticles(articleModel: List<ArticleModel>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteArticle(id: ArticleId) {
        TODO("Not yet implemented")
    }

    override suspend fun restoreArticle(id: ArticleId) {
        TODO("Not yet implemented")
    }
}