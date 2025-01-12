package com.davidmerchan.home.presentation.viewModel

import com.davidmerchan.home.domain.useCase.DeleteArticleUseCase
import com.davidmerchan.home.domain.useCase.GetArticlesUseCase
import com.davidmerchan.home.domain.useCase.RestoreArticleUseCase
import com.davidmerchan.home.presentation.HomeUIEvents
import com.davidmerchan.home.presentation.HomeUIState
import com.davidmerchan.home.presentation.model.Article
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArticlesViewModelTest {

    @get:Rule
    val mainDispatcherRule: MainDispatcherTest = MainDispatcherTest()

    private val getArticlesUseCase: GetArticlesUseCase = mockk()
    private val deleteArticleUseCase: DeleteArticleUseCase = mockk()
    private val restoreArticleUseCase: RestoreArticleUseCase = mockk()

    private lateinit var articlesViewModel: ArticlesViewModel

    @Before
    fun setUp() {
        articlesViewModel = ArticlesViewModel(
            getArticlesUseCase,
            deleteArticleUseCase,
            restoreArticleUseCase
        )

    }

    @After
    fun tearDown() {
        confirmVerified(
            getArticlesUseCase,
            deleteArticleUseCase,
            restoreArticleUseCase
        )
    }

    @Test
    fun `fetchArticles should update UI state with articles on success`() = runTest {
        // Given
        val article: Article = mockk()
        coEvery { getArticlesUseCase.invoke() } returns Result.success(listOf(article))

        // When
        articlesViewModel.handleEvents(HomeUIEvents.LoadArticles)

        // Then
        coVerify { getArticlesUseCase.invoke() }
        confirmVerified(article)

        assertEquals(
            HomeUIState(articles = listOf(article)),
            articlesViewModel.uiState.value
        )
    }

    @Test
    fun `fetchArticles should update UI state with error on failure`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { getArticlesUseCase.invoke() } returns Result.failure(exception)

        // When
        articlesViewModel.handleEvents(HomeUIEvents.LoadArticles)

        // Then
        coVerify { getArticlesUseCase.invoke() }

        assertEquals(
            HomeUIState(isError = true),
            articlesViewModel.uiState.value
        )
    }

    @Test
    fun `deleteArticle should update UI state on success`() = runTest {
        // Given
        val articleId = 1L
        coEvery { deleteArticleUseCase.invoke(articleId) } returns Result.success(Unit)

        // When
        articlesViewModel.handleEvents(HomeUIEvents.DeleteArticle(articleId))

        // Then
        assertEquals(
            HomeUIState(
                articles = emptyList(),
                successDeleted = articleId
            ),
            articlesViewModel.uiState.value
        )
        coVerify { deleteArticleUseCase.invoke(articleId) }
    }

    @Test
    fun `deleteArticle should update UI state with error on failure`() = runTest {
        // Given
        val articleId = 123L
        val exception = Exception("Deletion error")

        coEvery { deleteArticleUseCase(articleId) } returns Result.failure(exception)

        // When
        articlesViewModel.handleEvents(HomeUIEvents.DeleteArticle(articleId))

        // Then
        assertEquals(
            articlesViewModel.uiState.value.copy(errorDeleted = articleId),
            articlesViewModel.uiState.value
        )
        coVerify { deleteArticleUseCase(articleId) }
    }

    @Test
    fun `restoreArticle should update UI state on success`() = runTest {
        // Given
        val articleId = 123L
        val restoredArticle: Article = mockk(relaxed = true)

        every { restoredArticle.createdAt } returns "12-12-2024"
        coEvery { restoreArticleUseCase.invoke(articleId) } returns Result.success(restoredArticle)

        // When
        articlesViewModel.handleEvents(HomeUIEvents.RestoreArticle(articleId))

        // Then
        assertEquals(
            HomeUIState(
                articles = articlesViewModel.uiState.value.articles
                    .sortedByDescending { it.createdAt },
                successDeleted = null,
                errorDeleted = null
            ),
            articlesViewModel.uiState.value
        )
        coVerify {
            restoreArticleUseCase.invoke(articleId)
            restoredArticle.equals(any())
        }
        confirmVerified(restoredArticle)
    }

    @Test
    fun `restoreArticle should update UI state with error on failure`() = runTest {
        // Given
        val articleId = 123L
        val exception = Exception("Restoration error")
        coEvery { restoreArticleUseCase.invoke(articleId) } returns Result.failure(exception)

        // When
        articlesViewModel.handleEvents(HomeUIEvents.RestoreArticle(articleId))

        // Then
        coVerify { restoreArticleUseCase.invoke(articleId) }
    }

}
