package com.davidmerchan.home.presentation.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidmerchan.core.date.DateFormatter
import com.davidmerchan.designsystem.theme.RavenTechTestTheme
import com.davidmerchan.home.domain.model.ArticleModel

@Composable
fun ArticleItem(
    article: ArticleModel,
    modifier: Modifier = Modifier,
    onShowDetail: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onShowDetail() }
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = article.title,
            softWrap = true,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = article.author,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 16.sp
            )
            Text(
                text = article.createdAt.formatDate(context),
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}

@Preview
@Composable
private fun ArticleItemPreview() {
    RavenTechTestTheme {
        ArticleItem(
            article = ArticleModel(
                id = 1,
                title = "Sample Article",
                content = "Lorem ipsum",
                author = "John Doe",
                createdAt = "2022-01-01",
                storyUrl = "https://example.com/sample-article"
            ),
            onShowDetail = {

            }
        )
    }
}

private fun String.formatDate(context: Context): String {
    val date = DateFormatter.parseIsoStringToLocalDateTime(this)
    val dateFormated = DateFormatter.formatRelativeDate(context, date)
    return dateFormated
}
