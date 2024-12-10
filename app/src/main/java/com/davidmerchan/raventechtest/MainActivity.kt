package com.davidmerchan.raventechtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                }
            }
        }
    }
}
