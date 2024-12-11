package com.davidmerchan.home.domain.useCase

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

class DeleteArticleUseCaseTest {

    private val articlesLocalRepository: ArticlesLocalRepository = mockk()

    private lateinit var deleteArticleUseCase: DeleteArticleUseCase

    @Before
    fun setUp() {
        deleteArticleUseCase = DeleteArticleUseCase(articlesLocalRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(articlesLocalRepository)
    }

    @Test
    fun `invoke should delete article successfully`() = runBlocking {
        // Given
        val articleId = 123L
        coEvery { articlesLocalRepository.deleteArticle(articleId) } returns Result.success(Unit)

        // When
        val result = deleteArticleUseCase.invoke(articleId)

        // Then
        coVerify { articlesLocalRepository.deleteArticle(articleId) }
        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun `invoke should return failure when repository fails`() = runBlocking {
        // Given
        val articleId = 123L
        val exception = Exception("Database error")
        coEvery { articlesLocalRepository.deleteArticle(articleId) } returns Result.failure(exception)

        // When
        val result = deleteArticleUseCase.invoke(articleId)

        // Then
        coVerify { articlesLocalRepository.deleteArticle(articleId) }
        assertEquals(Result.failure<Unit>(exception), result)
    }

}