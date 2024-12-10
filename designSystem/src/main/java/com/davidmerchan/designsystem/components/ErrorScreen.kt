package com.davidmerchan.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.davidmerchan.designsystem.R
import com.davidmerchan.designsystem.theme.RavenTechTestTheme

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, error: String? = null) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(error ?: stringResource(R.string.general_error))
    }
}

@Preview
@Composable
internal fun ErrorScreenPreview() {
    RavenTechTestTheme {
        ErrorScreen()
    }
}
