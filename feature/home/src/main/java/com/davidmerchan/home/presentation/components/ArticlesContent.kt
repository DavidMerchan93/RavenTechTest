package com.davidmerchan.home.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davidmerchan.designsystem.components.SwipeToDeleteBox
import com.davidmerchan.home.domain.ArticleId
import com.davidmerchan.home.domain.ArticleModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ArticlesContent(
    articles: ImmutableList<ArticleModel>,
    onDeleteArticle: (id: ArticleId) -> Unit,
    modifier: Modifier = Modifier,
    onGoToDetail: (id: ArticleId, url: String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
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
                        onGoToDetail(article.id, article.storyUrl)
                    },
                )
            }
        }
    }
}
