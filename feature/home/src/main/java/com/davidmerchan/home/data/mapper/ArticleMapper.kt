package com.davidmerchan.home.data.mapper

import com.davidmerchan.database.entity.ArticleEntity
import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.network.model.Hits

fun Hits.mapToDomain(): ArticleModel {
    return ArticleModel(
        id = storyId,
        title = storyTitle ?: title ?: "N/A",
        storyUrl = storyUrl ?: url ?: "N/A",
        author = author,
        content = commentText ?: "N/A",
        createdAt = createdAt
    )
}

fun ArticleEntity.mapToDomain(): ArticleModel {
    return ArticleModel(
        id = id,
        title = title,
        content = content,
        author = author,
        createdAt = createdAt,
        storyUrl = storyUrl,
        isDeleted = isDeleted
    )
}
