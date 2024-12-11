package com.davidmerchan.home.data.repository

import com.davidmerchan.core.AppLogger
import com.davidmerchan.database.dao.ArticleDao
import com.davidmerchan.database.entity.ArticleEntity
import com.davidmerchan.home.data.mapper.mapToDomain
import com.davidmerchan.home.domain.model.ArticleModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesLocalDatasourceTest {

    private val logger: AppLogger = mockk()
    private val articlesDao: ArticleDao = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var articlesLocalDatasource: ArticlesLocalDatasource

    @Before
    fun setUp() {
        articlesLocalDatasource = ArticlesLocalDatasource(logger, articlesDao, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        confirmVerified(logger, articlesDao)
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllArticles() should return a list of articles on success`() = runBlocking {
        // Given
        val articleEntity: ArticleEntity = mockk {
            every { id } returns 1234L
            every { title } returns "title"
            every { content } returns "content"
            every { author } returns "author"
            every { createdAt } returns "createdAt"
            every { isDeleted } returns false
            every { storyUrl } returns "storyUrl"
        }
        val expectedResult = Result.success(
            listOf(
                ArticleModel(
                    id = 1234L,
                    title = "title",
                    storyUrl = "storyUrl",
                    author = "author",
                    content = "content",
                    createdAt = "createdAt"
                )
            )
        )

        coEvery { articlesDao.getArticles() } returns listOf(articleEntity)

        // When
        val result = articlesLocalDatasource.getAllArticles()

        // Then
        verify { articleEntity.mapToDomain() }
        coVerify { articlesDao.getArticles() }
        assertEquals(expectedResult, result)

        confirmVerified(articleEntity)
    }


    @Test
    fun `getAllArticles() should log and return failure on error`() = runBlocking {
        // Given
        val exception = IOException("Database error")

        coEvery { articlesDao.getArticles() } throws exception
        every { logger.log(exception) } just runs

        // When
        val result = articlesLocalDatasource.getAllArticles()

        // Then
        verify { logger.log(exception) }
        coVerify { articlesDao.getArticles() }
        assertEquals(Result.failure<List<ArticleModel>>(exception), result)
    }

    @Test
    fun `saveArticles() should save articles and return success`() = runBlocking {
        // Given
        val article: ArticleModel = mockk {
            every { id } returns 1234L
            every { title } returns "title"
            every { content } returns "content"
            every { author } returns "author"
            every { createdAt } returns "createdAt"
            every { isDeleted } returns false
            every { storyUrl } returns "storyUrl"
        }

        coEvery { articlesDao.insertArticles(*anyVararg()) } just runs

        // When
        val result = articlesLocalDatasource.saveArticles(listOf(article))

        // Then
        verify {
            article.id
            article.title
            article.content
            article.author
            article.createdAt
            article.isDeleted
            article.storyUrl
        }
        coVerify { articlesDao.insertArticles(*anyVararg()) }
        assertEquals(Result.success(Unit), result)

        confirmVerified(article)
    }

    @Test
    fun `saveArticles() should log and return failure on error`() = runBlocking {
        // Given
        val article: ArticleModel = mockk {
            every { id } returns 1234L
            every { title } returns "title"
            every { content } returns "content"
            every { author } returns "author"
            every { createdAt } returns "createdAt"
            every { isDeleted } returns false
            every { storyUrl } returns "storyUrl"
        }

        val exception = IOException("Insertion error")

        coEvery { articlesDao.insertArticles(*anyVararg()) } throws exception
        every { logger.log(exception) } just runs

        // When
        val result = articlesLocalDatasource.saveArticles(listOf(article))

        // Then
        verify {
            logger.log(exception)
            article.id
            article.title
            article.content
            article.author
            article.createdAt
            article.isDeleted
            article.storyUrl
        }
        coVerify { articlesDao.insertArticles(*anyVararg()) }
        assertEquals(Result.failure<Unit>(exception), result)

        confirmVerified(article)
    }

    @Test
    fun `deleteArticle() should delete article and return success`() = runBlocking {
        // Given
        val articleId = 1234L
        coEvery { articlesDao.deleteArticle(articleId) } just runs

        // When
        val result = articlesLocalDatasource.deleteArticle(articleId)

        // Then
        coVerify { articlesDao.deleteArticle(articleId) }
        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun `deleteArticle() should log and return failure on error`() = runBlocking {
        // Given
        val articleId = 123L
        val exception = IOException("Deletion error")

        coEvery { articlesDao.deleteArticle(articleId) } throws exception
        every { logger.log(exception) } just runs

        // When
        val result = articlesLocalDatasource.deleteArticle(articleId)

        // Then
        verify { logger.log(exception) }
        coVerify { articlesDao.deleteArticle(articleId) }
        assertEquals(Result.failure<Unit>(exception), result)
    }

    @Test
    fun `restoreArticle() should restore article and return success`() = runBlocking {
        // Given
        val articleId = 1234L
        val article: ArticleEntity = mockk {
            every { id } returns 1234L
            every { title } returns "title"
            every { content } returns "content"
            every { author } returns "author"
            every { createdAt } returns "createdAt"
            every { isDeleted } returns false
            every { storyUrl } returns "storyUrl"
        }
        val expectedResult = Result.success(
            ArticleModel(
                id = 1234L,
                title = "title",
                storyUrl = "storyUrl",
                author = "author",
                content = "content",
                createdAt = "createdAt"
            )
        )

        coEvery { articlesDao.restore(articleId) } returns article

        // When
        val result = articlesLocalDatasource.restoreArticle(articleId)

        // Then
        verify {
            article.id
            article.title
            article.content
            article.author
            article.createdAt
            article.isDeleted
            article.storyUrl
        }
        coVerify { articlesDao.restore(articleId) }
        assertEquals(expectedResult, result)

        confirmVerified(article)
    }

    @Test
    fun `restoreArticle() should log and return failure on error`() = runBlocking {
        // Given
        val articleId = 1234L
        val exception = IOException("Restoration error")

        coEvery { articlesDao.restore(articleId) } throws exception
        every { logger.log(exception) } just runs

        // When
        val result = articlesLocalDatasource.restoreArticle(articleId)

        // Then
        verify { logger.log(exception) }
        coVerify { articlesDao.restore(articleId) }
        assertEquals(Result.failure<ArticleModel>(exception), result)
    }
}
