package com.davidmerchan.home.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.domain.useCase.DeleteArticleUseCase
import com.davidmerchan.home.domain.useCase.GetArticlesUseCase
import com.davidmerchan.home.presentation.HomeUIEvents
import com.davidmerchan.home.presentation.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        getArticles()
    }

    fun handleEvents(event: HomeUIEvents) {
        when (event) {
            HomeUIEvents.LoadArticles -> onPullToRefreshArticles()
            is HomeUIEvents.DeleteArticle -> deleteArticle(event.id)
            is HomeUIEvents.RestoreArticle -> TODO()
        }
    }

    private fun onPullToRefreshArticles() {
        _uiState.update {
            it.copy(isRefreshing = true)
        }
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getArticlesUseCase()
            when {
                result.isSuccess -> {
                    val articles = result.getOrNull() ?: emptyList()
                    _uiState.value = HomeUIState(articles = articles)
                }

                result.isFailure -> {
                    _uiState.value = HomeUIState(isError = true)
                }
            }
        }
    }

    private fun deleteArticle(id: ArticleId) {
        viewModelScope.launch {
            val result = deleteArticleUseCase(id)
            when {
                result.isSuccess -> {
                    _uiState.update {
                        HomeUIState(
                            articles = it.articles.filterNot { article -> article.id == id },
                            successDeleted = id
                        )
                    }
                }

                result.isFailure -> HomeUIState(errorDeleted = id)
            }
        }
    }


}
