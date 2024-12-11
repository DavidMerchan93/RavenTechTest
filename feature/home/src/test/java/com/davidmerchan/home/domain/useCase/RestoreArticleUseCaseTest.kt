package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RestoreArticleUseCaseTest {

    private val articlesLocalRepository: ArticlesLocalRepository = mockk()

    private lateinit var restoreArticleUseCase: RestoreArticleUseCase

    @Before
    fun setUp() {
        restoreArticleUseCase = RestoreArticleUseCase(articlesLocalRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(articlesLocalRepository)
    }

    @Test
    fun `invoke should restore article successfully`() = runBlocking {
        // Given
        val articleId = 123L
        val restoredArticle: ArticleModel = mockk()

        coEvery { articlesLocalRepository.restoreArticle(articleId) } returns Result.success(
            restoredArticle
        )

        // When
        val result = restoreArticleUseCase.invoke(articleId)

        // Then
        coVerify { articlesLocalRepository.restoreArticle(articleId) }
        assertEquals(Result.success(restoredArticle), result)
    }

    @Test
    fun `invoke should return failure when repository fails`() = runBlocking {
        // Given
        val articleId = 123L
        val exception = Exception("Database error")
        coEvery { articlesLocalRepository.restoreArticle(articleId) } returns Result.failure(
            exception
        )

        // When
        val result = restoreArticleUseCase.invoke(articleId)

        // Then
        coVerify { articlesLocalRepository.restoreArticle(articleId) }
        assertEquals(Result.failure<ArticleModel>(exception), result)
    }
}