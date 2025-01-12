package com.davidmerchan.home.data.repository

import com.davidmerchan.core.AppLogger
import com.davidmerchan.home.data.mapper.mapToDomain
import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.network.api.ArticlesApi
import com.davidmerchan.network.model.ArticlesResponse
import com.davidmerchan.network.model.Hits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesRemoteDatasourceTest {

    private val logger: AppLogger = mockk()
    private val api: ArticlesApi = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var articlesRemoteDatasource: ArticlesRemoteDatasource

    @Before
    fun setUp() {
        articlesRemoteDatasource = ArticlesRemoteDatasource(logger, api, testDispatcher)
    }

    @After
    fun tearDown() {
        confirmVerified(logger, api)
        testDispatcher.cancel()
    }


    @Test
    fun `getArticles() should return a list of articles on success`() = runBlocking {
        // Given
        val apiResponse: ArticlesResponse = mockk()
        val article: Hits = mockk {
            every { storyId } returns 1234L
            every { storyTitle } returns "Title"
            every { storyUrl } returns "https://example.com"
            every { author } returns "Author"
            every { commentText } returns "Comment"
            every { createdAt } returns "2010-11-22"
        }

        val expectedArticles = listOf(article)
        val expectedResult = Result.success(
            listOf(
                ArticleModel(
                    id = 1234L,
                    title = "Title",
                    storyUrl = "https://example.com",
                    author = "Author",
                    content = "Comment",
                    createdAt = "2010-11-22"
                )
            )
        )

        every { apiResponse.hits } returns expectedArticles
        coEvery { api.getArticles() } returns apiResponse

        // When
        val result = articlesRemoteDatasource.getArticles()

        // Then
        verify {
            apiResponse.hits
            article.mapToDomain()
        }
        coVerify {
            api.getArticles()
        }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getArticles() should throw an exception on failure`() = runBlocking {
        // Given
        val exception = IOException("Network error")

        coEvery { api.getArticles() } throws exception
        every { logger.log(exception) } just runs

        // When
        val result = articlesRemoteDatasource.getArticles()

        // Then
        verify {
            logger.log(exception)
        }
        coVerify { api.getArticles() }
        assertEquals(Result.failure<Exception>(exception), result)
    }

    @Test
    fun `getArticles() should throw an exception on failure when have a mapping error`() =
        runBlocking {
            // Given
            val apiResponse: ArticlesResponse = mockk()

            coEvery { api.getArticles() } returns apiResponse
            every { logger.log(any<Exception>()) } just runs

            // When
            val result = articlesRemoteDatasource.getArticles()

            // Then
            verify {
                logger.log(any<Exception>())
            }
            coVerify { api.getArticles() }
            assertTrue(result.isFailure)
        }

}
