package com.example.movieknowledgeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieknowledgeapp.screens.AddMoviesScreen
import com.example.movieknowledgeapp.screens.MainScreen
import com.example.movieknowledgeapp.screens.SearchActorScreen
import com.example.movieknowledgeapp.screens.SearchByTitleScreen
import com.example.movieknowledgeapp.screens.SearchMovieScreen
import com.example.movieknowledgeapp.ui.theme.MovieTesterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieTesterTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.MAIN
                ) {
                    composable(Routes.MAIN) { MainScreen(navController) }
                    composable(Routes.ADD_MOVIES) { AddMoviesScreen(navController) }
                    composable(Routes.SEARCH_MOVIE) { SearchMovieScreen(navController) }
                    composable(Routes.SEARCH_ACTORS) { SearchActorScreen(navController) }
                    composable(Routes.SEARCH_BY_TITLE) { SearchByTitleScreen(navController) }
                }
            }
        }
    }
}

object Routes {
    const val MAIN = "main"
    const val ADD_MOVIES = "addMovies"
    const val SEARCH_MOVIE = "searchMovie"
    const val SEARCH_ACTORS = "searchActors"
    const val SEARCH_BY_TITLE = "searchByTitle"
}