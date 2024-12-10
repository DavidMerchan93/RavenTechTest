package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import javax.inject.Inject

class RestoreArticleUseCase @Inject constructor(
    private val articlesLocalRepository: ArticlesLocalRepository
) {
    suspend operator fun invoke(id: ArticleId): Result<ArticleModel> =
        articlesLocalRepository.restoreArticle(id)
}
