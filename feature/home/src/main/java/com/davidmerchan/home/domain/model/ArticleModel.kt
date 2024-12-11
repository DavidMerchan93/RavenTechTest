package com.davidmerchan.home.domain.model

import com.davidmerchan.database.entity.ArticleEntity
import com.davidmerchan.home.presentation.model.Article

typealias ArticleId = Long

data class ArticleModel(
    val id: ArticleId,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: String,
    val storyUrl: String,
    val isDeleted: Boolean = false
)

fun ArticleModel.mapToPresentation(): Article {
    return Article(
        id = id,
        title = title,
        author = author,
        createdAt = createdAt,
        isDeleted = isDeleted,
        url = storyUrl
    )
}

fun ArticleModel.mapToEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        content = content,
        author = author,
        createdAt = createdAt,
        storyUrl = storyUrl,
        isDeleted = isDeleted
    )
}
