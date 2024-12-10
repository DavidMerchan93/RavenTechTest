package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import javax.inject.Inject

class SaveArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesLocalRepository
) {
    suspend operator fun invoke(articles: List<ArticleModel>) {
        articlesRepository.saveArticles(articles)
    }
}
