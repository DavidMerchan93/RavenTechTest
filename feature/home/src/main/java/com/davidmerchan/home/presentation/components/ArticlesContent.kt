package com.davidmerchan.home.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davidmerchan.designsystem.components.SwipeToDeleteBox
import com.davidmerchan.home.domain.model.ArticleId
import com.davidmerchan.home.domain.model.ArticleModel
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesContent(
    articles: ImmutableList<ArticleModel>,
    onDeleteArticle: (id: ArticleId) -> Unit,
    isRefreshing: Boolean,
    onGoToDetail: (title: String, url: String) -> Unit,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        LazyColumn {
            items(
                items = articles,
                key = { it.id }
            ) { article ->
                SwipeToDeleteBox(
                    modifier = Modifier.animateItem(),
                    onDelete = {
                        onDeleteArticle(article.id)
                    }
                ) {
                    ArticleItem(
                        article = article,
                        onShowDetail = {
                            onGoToDetail(article.title, article.storyUrl)
                        },
                    )
                }
            }
        }
    }
}
