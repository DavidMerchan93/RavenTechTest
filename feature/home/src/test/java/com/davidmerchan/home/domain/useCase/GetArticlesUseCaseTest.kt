package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesRemoteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetArticlesUseCaseTest {

    private val articlesRemote: ArticlesRemoteRepository = mockk()
    private val saveArticlesUseCase: SaveArticlesUseCase = mockk()
    private val getLocalArticlesUseCase: GetLocalArticlesUseCase = mockk()

    private lateinit var getArticlesUseCase: GetArticlesUseCase

    @Before
    fun setUp() {
        getArticlesUseCase = GetArticlesUseCase(
            articlesRemote,
            saveArticlesUseCase,
            getLocalArticlesUseCase
        )
    }

    @After
    fun tearDown() {
        confirmVerified(
            articlesRemote,
            saveArticlesUseCase,
            getLocalArticlesUseCase
        )
    }


    @Test
    fun `invoke should return local articles after saving remote articles on success`() = runBlocking {
        // Given
        val remoteArticles = listOf(mockk<ArticleModel>())
        val localArticles = listOf(mockk<ArticleModel>())

        coEvery { articlesRemote.getArticles() } returns Result.success(remoteArticles)
        coEvery { saveArticlesUseCase.invoke(remoteArticles) } returns Result.success(Unit)
        coEvery { getLocalArticlesUseCase.invoke() } returns Result.success(localArticles)

        // When
        val result = getArticlesUseCase.invoke()

        // Then
        coVerify {
            articlesRemote.getArticles()
            saveArticlesUseCase.invoke(remoteArticles)
            getLocalArticlesUseCase.invoke()
        }
        assertEquals(Result.success(localArticles), result)
    }

    @Test
    fun `invoke should return local articles even if remote call fails`() = runBlocking {
        // Given
        val localArticles = listOf(mockk<ArticleModel>())
        coEvery { articlesRemote.getArticles() } returns Result.failure(Exception("Network error"))
        coEvery { getLocalArticlesUseCase.invoke() } returns Result.success(localArticles)

        // When
        val result = getArticlesUseCase.invoke()

        // Then
        coVerify {
            articlesRemote.getArticles()
            getLocalArticlesUseCase.invoke()
        }
        assertEquals(Result.success(localArticles), result)
    }

    @Test
    fun `invoke should return failure if both remote and local calls fail`() = runBlocking {
        // Given
        val exception = Exception("Both remote and local calls failed")
        coEvery { articlesRemote.getArticles() } returns Result.failure(Exception("Network error"))
        coEvery { getLocalArticlesUseCase.invoke() } returns Result.failure(exception)

        // When
        val result = getArticlesUseCase.invoke()

        // Then
        coVerify {
            articlesRemote.getArticles()
            getLocalArticlesUseCase.invoke()
        }
        assertEquals(Result.failure<List<ArticleModel>>(exception), result)
    }
}