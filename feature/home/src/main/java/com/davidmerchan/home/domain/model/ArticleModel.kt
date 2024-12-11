package com.davidmerchan.home.domain.model

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
