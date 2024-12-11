package com.davidmerchan.home.presentation.model

import android.content.Context
import com.davidmerchan.core.date.DateFormatter
import com.davidmerchan.home.domain.model.ArticleId

data class Article(
    val id: ArticleId,
    val title: String,
    val author: String,
    val createdAt: String,
    val url: String,
    val isDeleted: Boolean = false
) {

    fun formatCreatedAt(context: Context): String {
        val date = DateFormatter.parseIsoStringToLocalDateTime(createdAt)
        val dateFormated = DateFormatter.formatRelativeDate(context, date)
        return dateFormated
    }
}
