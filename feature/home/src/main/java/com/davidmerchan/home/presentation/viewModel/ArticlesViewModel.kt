package com.davidmerchan.home.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.domain.useCase.DeleteArticleUseCase
import com.davidmerchan.home.domain.useCase.GetArticlesUseCase
import com.davidmerchan.home.domain.useCase.RestoreArticleUseCase
import com.davidmerchan.home.presentation.HomeUIEvents
import com.davidmerchan.home.presentation.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val restoreArticleUseCase: RestoreArticleUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    fun handleEvents(event: HomeUIEvents) {
        when (event) {
            HomeUIEvents.LoadArticles -> getArticles()
            HomeUIEvents.ReLoadArticles -> onPullToRefreshArticles()
            is HomeUIEvents.DeleteArticle -> deleteArticle(event.id)
            is HomeUIEvents.RestoreArticle -> restoreArticle(event.id)
        }
    }

    private fun onPullToRefreshArticles() {
        _uiState.update {
            it.copy(isRefreshing = true)
        }
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
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

                result.isFailure -> {
                    _uiState.update {
                        it.copy(errorDeleted = id)
                    }
                }
            }
        }
    }


    private fun restoreArticle(id: ArticleId) {
        viewModelScope.launch {
            val result = restoreArticleUseCase(id)
            when {
                result.isSuccess && result.getOrNull() != null -> {
                    val articles = _uiState.value.articles
                    val article = requireNotNull(result.getOrNull())

                    _uiState.value = HomeUIState(
                        articles = articles.plus(article)
                            .sortedByDescending { it.createdAt },
                        successDeleted = null,
                        errorDeleted = null
                    )
                }

                result.isFailure -> Unit
            }
        }
    }

}
