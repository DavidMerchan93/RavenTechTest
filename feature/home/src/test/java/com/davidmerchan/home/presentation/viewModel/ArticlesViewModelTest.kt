package com.davidmerchan.home.presentation.viewModel

import com.davidmerchan.home.domain.useCase.DeleteArticleUseCase
import com.davidmerchan.home.domain.useCase.GetArticlesUseCase
import com.davidmerchan.home.domain.useCase.RestoreArticleUseCase
import io.mockk.confirmVerified
import io.mockk.mockk
import org.junit.After
import org.junit.Before

class ArticlesViewModelTest {

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

}