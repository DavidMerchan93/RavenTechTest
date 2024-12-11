package com.davidmerchan.home.data.repository

import com.davidmerchan.core.AppLogger
import com.davidmerchan.di.IODispatcher
import com.davidmerchan.home.data.mapper.mapToDomain
import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesRemoteRepository
import com.davidmerchan.network.api.ArticlesApi
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@Suppress("TooGenericExceptionCaught")
class ArticlesRemoteDatasource @Inject constructor(
    private val logger: AppLogger,
    private val api: ArticlesApi,
    @IODispatcher private val ioDispatcher: CoroutineContext
) : ArticlesRemoteRepository {
    override suspend fun getArticles(): Result<List<ArticleModel>> {
        return withContext(ioDispatcher) {
            try {
                val response = api.getArticles()
                Result.success(response.hits.map { it.mapToDomain() })
            } catch (e: Exception) {
                logger.log(e)
                Result.failure(e)
            }
        }
    }
}
