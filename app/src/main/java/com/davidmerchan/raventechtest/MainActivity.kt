package com.davidmerchan.raventechtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.davidmerchan.designsystem.theme.RavenTechTestTheme
import com.davidmerchan.detail.presentation.ArticleDetailScreen
import com.davidmerchan.home.presentation.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            RavenTechTestTheme {
                NavHost(navController = navController, startDestination = Screens.Home) {
                    composable<Screens.Home> {
                        HomeScreen(
                            goToDetail = { title, url ->
                                navController.navigate(Screens.Detail(title, url))
                            }
                        )
                    }
                    composable<Screens.Detail> {
                        val arg = it.toRoute<Screens.Detail>()
                        ArticleDetailScreen(
                            title = arg.title,
                            url = arg.url,
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable<Screens.DeepLinkDetail>(
                        deepLinks = listOf(
                            navDeepLink<Screens.DeepLinkDetail>(basePath = "http://davidmerchan.com/detail/")
                        )
                    ) {
                        val arg = it.toRoute<Screens.DeepLinkDetail>()
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("DeepLink Detail: ${arg.title}")
                        }
                    }
                }
            }
        }
    }
}
