package com.davidmerchan.home.data.repository

import com.davidmerchan.home.data.mapper.mapToDomain
import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesRemoteRepository
import com.davidmerchan.network.api.ArticlesApi
import javax.inject.Inject

class ArticlesRemoteDatasource @Inject constructor(
    private val api: ArticlesApi
) : ArticlesRemoteRepository {
    override suspend fun getArticles(): Result<List<ArticleModel>> {
        return try {
            val response = api.getArticles()
            Result.success(response.hits.mapToDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}