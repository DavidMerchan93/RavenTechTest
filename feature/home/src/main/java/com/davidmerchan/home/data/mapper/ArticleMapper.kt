package com.davidmerchan.home.data.mapper

import com.davidmerchan.home.domain.model.ArticleModel
import com.davidmerchan.network.model.Hits

fun List<Hits>.mapToDomain(): List<ArticleModel> {
    return map {
        ArticleModel(
            id = it.storyId,
            title = it.storyTitle ?: "",
            storyUrl = it.storyUrl ?: "",
            author = it.author,
            content = it.commentText ?: "",
            createdAt = it.createdAt
        )
    }
}