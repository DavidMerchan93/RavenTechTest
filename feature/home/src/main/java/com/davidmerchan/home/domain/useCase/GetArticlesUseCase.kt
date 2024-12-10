package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesRemoteRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articlesRemote: ArticlesRemoteRepository,
    private val saveArticlesUseCase: SaveArticlesUseCase,
    private val getLocalArticlesUseCase: GetLocalArticlesUseCase
) {

    suspend operator fun invoke(): Result<List<ArticleModel>> {
        val responseRemote = articlesRemote.getArticles()

        return Result.success(orderArticlesByDate(responseRemote.getOrNull() ?: emptyList()))
        /*if (responseRemote.isSuccess) {
            val remoteArticles: List<ArticleModel> = responseRemote.getOrNull() ?: emptyList()
            saveArticlesUseCase(remoteArticles)
            return responseRemote
        }

        return getLocalArticlesUseCase()*/
    }

    private fun orderArticlesByDate(articles: List<ArticleModel>): List<ArticleModel> {
        return articles.filter { it.isDeleted.not() }
            .sortedByDescending { it.createdAt }
            .distinctBy { it.id }
    }
}
