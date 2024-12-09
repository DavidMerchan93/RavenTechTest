package com.davidmerchan.raventechtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.davidmerchan.designsystem.RavenTechTestTheme
import com.davidmerchan.detail.presentation.ArticleDetailScreen
import com.davidmerchan.home.presentation.HomeScreen

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
                            goToDetail = {
                                navController.navigate(Screens.Detail(articleId = 1))
                            }
                        )
                    }
                    composable<Screens.Detail> {
                        ArticleDetailScreen()
                    }
                }
            }
        }
    }
}
