package com.davidmerchan.home.data.mapper

import com.davidmerchan.database.entity.ArticleEntity
import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.network.model.Hits

fun Hits.mapToDomain(): ArticleModel {
    return ArticleModel(
        id = storyId,
        title = storyTitle ?: "",
        storyUrl = storyUrl ?: "",
        author = author,
        content = commentText ?: "",
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

fun ArticleModel.toEntity(): ArticleEntity {
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
