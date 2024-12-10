package com.davidmerchan.home.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.home.domain.useCase.GetArticlesUseCase
import com.davidmerchan.home.presentation.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
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

}
