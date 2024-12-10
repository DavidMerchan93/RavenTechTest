package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import javax.inject.Inject

class GetLocalArticlesUseCase @Inject constructor(
    private val articlesLocalRepository: ArticlesLocalRepository
) {
    suspend operator fun invoke(): Result<List<ArticleModel>> {
        val localArticles = articlesLocalRepository.getAllArticles()
        return if (localArticles.isSuccess) {
            val data = localArticles.getOrNull() ?: emptyList()
            return Result.success(orderArticlesByDate(data))
        } else {
            localArticles
        }
    }

    private fun orderArticlesByDate(articles: List<ArticleModel>): List<ArticleModel> {
        return articles.filter { it.isDeleted.not() }
            .sortedByDescending { it.createdAt }
            .distinctBy { it.id }
    }
}
