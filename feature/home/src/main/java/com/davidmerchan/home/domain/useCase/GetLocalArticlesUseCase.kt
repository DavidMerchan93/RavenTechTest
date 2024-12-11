package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import com.davidmerchan.home.presentation.model.Article
import javax.inject.Inject

class GetLocalArticlesUseCase @Inject constructor(
    private val articlesLocalRepository: ArticlesLocalRepository
) {
    suspend operator fun invoke(): Result<List<Article>> {
        val localArticles = articlesLocalRepository.getAllArticles()
        val data = localArticles.getOrNull() ?: emptyList()

        return if (localArticles.isSuccess) {
            Result.success(orderArticlesByDate(data))
        } else {
            Result.failure(localArticles.exceptionOrNull()!!)
        }
    }

    private fun orderArticlesByDate(articles: List<ArticleModel>): List<Article> {
        return articles.filter { it.isDeleted.not() }
            .sortedByDescending { it.createdAt }
            .distinctBy { it.id }
            .map {
                Article(
                    id = it.id,
                    title = it.title,
                    author = it.author,
                    createdAt = it.createdAt,
                    url = it.storyUrl,
                    isDeleted = it.isDeleted
                )
            }
    }
}
