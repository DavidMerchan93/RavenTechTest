package com.davidmerchan.home.presentation.viewModel

import com.davidmerchan.home.domain.useCase.GetArticlesUseCase
import io.mockk.confirmVerified
import io.mockk.mockk
import org.junit.After
import org.junit.Before

class ArticlesViewModelTest {

    private val getArticlesUseCase: GetArticlesUseCase = mockk()

    private lateinit var articlesViewModel: ArticlesViewModel

    @Before
    fun setUp() {
        articlesViewModel = ArticlesViewModel(getArticlesUseCase)
    }

    @After
    fun tearDown() {
        confirmVerified(getArticlesUseCase)
    }

}