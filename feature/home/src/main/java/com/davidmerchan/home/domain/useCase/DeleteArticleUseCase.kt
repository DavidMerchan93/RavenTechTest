package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    private val articlesLocalRepository: ArticlesLocalRepository
) {
    suspend operator fun invoke(id: ArticleId): Result<Unit> =
        articlesLocalRepository.deleteArticle(id)
}
