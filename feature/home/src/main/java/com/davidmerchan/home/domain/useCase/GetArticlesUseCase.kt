package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesRemoteRepository
import com.davidmerchan.home.presentation.model.Article
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articlesRemote: ArticlesRemoteRepository,
    private val saveArticlesUseCase: SaveArticlesUseCase,
    private val getLocalArticlesUseCase: GetLocalArticlesUseCase
) {

    suspend operator fun invoke(): Result<List<Article>> {
        val responseRemote = articlesRemote.getArticles()

        if (responseRemote.isSuccess) {
            val remoteArticles: List<ArticleModel> = responseRemote.getOrNull() ?: emptyList()
            saveArticlesUseCase(remoteArticles)
        }

        return getLocalArticlesUseCase()
    }
}
