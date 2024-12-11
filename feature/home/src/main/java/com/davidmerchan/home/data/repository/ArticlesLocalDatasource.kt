package com.davidmerchan.home.data.repository

import com.davidmerchan.core.AppLogger
import com.davidmerchan.database.dao.ArticleDao
import com.davidmerchan.di.IODispatcher
import com.davidmerchan.home.data.mapper.mapToDomain
import com.davidmerchan.home.data.mapper.toEntity
import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@Suppress("TooGenericExceptionCaught")
class ArticlesLocalDatasource @Inject constructor(
    private val logger: AppLogger,
    private val articlesDao: ArticleDao,
    @IODispatcher private val ioDispatcher: CoroutineContext
) : ArticlesLocalRepository {
    override suspend fun getAllArticles(): Result<List<ArticleModel>> {
        return withContext(ioDispatcher) {
            try {
                val articles = articlesDao.getArticles().map { it.mapToDomain() }
                Result.success(articles)
            } catch (e: Exception) {
                logger.log(e)
                Result.failure(e)
            }
        }
    }

    @Suppress("SpreadOperator")
    override suspend fun saveArticles(articleModel: List<ArticleModel>): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val data = articleModel.map { it.toEntity() }.toTypedArray()
                articlesDao.insertArticles(*data)
                Result.success(Unit)
            } catch (e: Exception) {
                logger.log(e)
                Result.failure(e)
            }
        }
    }

    override suspend fun deleteArticle(id: ArticleId): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                articlesDao.deleteArticle(id)
                Result.success(Unit)
            } catch (e: Exception) {
                logger.log(e)
                Result.failure(e)
            }
        }
    }

    override suspend fun restoreArticle(id: ArticleId): Result<ArticleModel> {
        return withContext(ioDispatcher) {
            try {
                val article = articlesDao.restore(id)
                Result.success(article.mapToDomain())
            } catch (e: Exception) {
                logger.log(e)
                Result.failure(e)
            }
        }
    }
}
