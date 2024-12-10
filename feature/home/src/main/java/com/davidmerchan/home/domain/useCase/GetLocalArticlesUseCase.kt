package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import javax.inject.Inject

class GetLocalArticlesUseCase @Inject constructor(
    private val articlesLocalRepository: ArticlesLocalRepository
) {
    suspend operator fun invoke(): Result<List<ArticleModel>> {
        return articlesLocalRepository.getAllArticles()
    }
}
