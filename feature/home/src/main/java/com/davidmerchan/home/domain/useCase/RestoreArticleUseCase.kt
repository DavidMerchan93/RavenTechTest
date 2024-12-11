package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.domain.model.mapToPresentation
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import com.davidmerchan.home.presentation.model.Article
import javax.inject.Inject

class RestoreArticleUseCase @Inject constructor(
    private val articlesLocalRepository: ArticlesLocalRepository
) {
    suspend operator fun invoke(id: ArticleId): Result<Article> {
        val result = articlesLocalRepository.restoreArticle(id)
        return if (result.isSuccess) {
            Result.success(result.getOrNull()!!.mapToPresentation())
        } else {
            Result.failure(result.exceptionOrNull()!!)
        }
    }

}
