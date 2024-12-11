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

class SaveArticlesUseCaseTest {

    private val articlesRepository: ArticlesLocalRepository = mockk()

    private lateinit var saveArticlesUseCase: SaveArticlesUseCase

    @Before
    fun setUp() {
        saveArticlesUseCase = SaveArticlesUseCase(articlesRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(articlesRepository)
    }

    @Test
    fun `invoke should save articles successfully`() = runBlocking {
        // Given
        val article: ArticleModel = mockk()

        coEvery { articlesRepository.saveArticles(listOf(article)) } returns Result.success(Unit)

        // When
        val result = saveArticlesUseCase.invoke(listOf(article))

        // Then
        coVerify { articlesRepository.saveArticles(listOf(article)) }
        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun `invoke should return failure when repository fails`() = runBlocking {
        // Given
        val article: ArticleModel = mockk()
        val exception = Exception("Database error")

        coEvery { articlesRepository.saveArticles(listOf(article)) } returns Result.failure(exception)

        // When
        val result = saveArticlesUseCase.invoke(listOf(article))

        // Then
        coVerify { articlesRepository.saveArticles(listOf(article)) }
        assertEquals(Result.failure<Unit>(exception), result)
    }

}
