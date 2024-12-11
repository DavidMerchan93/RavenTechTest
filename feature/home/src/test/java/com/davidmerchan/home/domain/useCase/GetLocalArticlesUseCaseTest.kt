package com.davidmerchan.home.domain.useCase

import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.home.domain.repository.ArticlesLocalRepository
import com.davidmerchan.home.presentation.model.Article
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetLocalArticlesUseCaseTest {

    private val articlesLocalRepository: ArticlesLocalRepository = mockk()

    private lateinit var getLocalArticlesUseCase: GetLocalArticlesUseCase

    @Before
    fun setUp() {
        getLocalArticlesUseCase = GetLocalArticlesUseCase(articlesLocalRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(articlesLocalRepository)
    }

    @Test
    fun `invoke should return a list of articles ordered by date on success`() = runBlocking {
        // Given
        val articles = listOf(
            ArticleModel(
                id = 1L,
                createdAt = "createdAt",
                isDeleted = false,
                title = "Title 1",
                content = "Content 1",
                author = "Author 1",
                storyUrl = "Story URL 1"
            ),
            ArticleModel(
                id = 2L,
                createdAt = "createdAt",
                isDeleted = false,
                title = "Title 2",
                content = "Content 2",
                author = "Author 2",
                storyUrl = "Story URL 2"
            ),
            ArticleModel(
                id = 3L,
                createdAt = "createdAt",
                isDeleted = true,
                title = "Title 3",
                content = "Content 3",
                author = "Author 3",
                storyUrl = "Story URL 3"
            )
        )

        val expectedResult = Result.success(
            listOf(
                Article(
                    id = 1L,
                    createdAt = "createdAt",
                    isDeleted = false,
                    title = "Title 1",
                    author = "Author 1",
                    url = "Story URL 1"
                ),
                Article(
                    id = 2L,
                    createdAt = "createdAt",
                    isDeleted = false,
                    title = "Title 2",
                    author = "Author 2",
                    url = "Story URL 2"
                ),
            )
        )

        coEvery { articlesLocalRepository.getAllArticles() } returns Result.success(articles)

        // When
        val result = getLocalArticlesUseCase.invoke()

        // Then
        coVerify { articlesLocalRepository.getAllArticles() }

        assertEquals(expectedResult, result)
    }

    @Test
    fun `invoke should return failure when repository fails`() = runBlocking {
        // Given
        val exception = Exception("Database error")
        val exceptionExpected =

        coEvery { articlesLocalRepository.getAllArticles() } returns Result.failure(exception)

        // When
        val result = getLocalArticlesUseCase.invoke()

        // Then
        coVerify { articlesLocalRepository.getAllArticles() }
        assertEquals(Result.failure<List<Article>>(exception), result)
    }

}
