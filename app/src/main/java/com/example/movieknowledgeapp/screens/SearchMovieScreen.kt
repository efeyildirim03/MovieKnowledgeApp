package com.example.movieknowledgeapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieknowledgeapp.data.Movie
import com.example.movieknowledgeapp.data.MovieDatabase
import com.example.movieknowledgeapp.network.OmdbApiService
import kotlinx.coroutines.launch
import com.example.movieknowledgeapp.components.BackToMainButton
import kotlinx.coroutines.flow.first

@Composable
fun SearchMovieScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    var movie by remember { mutableStateOf<Movie?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = remember { MovieDatabase.getDatabase(context) }
    val movieDao = remember { database.movieDao() }
    var showBackButton by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search Section
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Movie Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Retrieve Movie Button
            Button(
                onClick = {
                    if (query.isBlank()) {
                        errorMessage = "Please enter a movie title"
                        return@Button
                    }

                    scope.launch {
                        isLoading = true
                        errorMessage = null
                        try {
                            movie = OmdbApiService.fetchMovieDetails(query)
                            if (movie == null) {
                                errorMessage = "Movie not found"
                            }
                        } catch (e: Exception) {
                            errorMessage = "Error: ${e.localizedMessage}"
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = !isLoading && query.isNotBlank()
            ) {
                Text(if (isLoading) "Searching..." else "Retrieve Movie")
            }

            // Save Movie Button
            Button(
                onClick = {
                    movie?.let { currentMovie ->
                        scope.launch {
                            try {
                                movieDao.insert(currentMovie)
                                // Add this debug code:
                                val count = movieDao.getAllMovies().first().size
                                errorMessage = "Movie saved! Total movies: $count"
                                println("DEBUG: Inserted movie. Total count: $count")
                            } catch (e: Exception) {
                                errorMessage = "Save failed: ${e.localizedMessage}"
                                println("DEBUG: Save error: ${e.stackTraceToString()}")
                            }
                        }
                    }
                }
            ){
                Text("Save to Database")
            }
        }

        // Error Message
        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Loading Indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Movie Details Display
        movie?.let { currentMovie ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Title: ${currentMovie.title}", fontWeight = FontWeight.Bold)
                Text("Year: ${currentMovie.year}")
                Text("Rated: ${currentMovie.rated}")
                Text("Released: ${currentMovie.released}")
                Text("Runtime: ${currentMovie.runtime}")
                Text("Genre: ${currentMovie.genre}")
                Text("Director: ${currentMovie.director}")
                Text("Writer: ${currentMovie.writer}")
                Text("Actors: ${currentMovie.actors}")
                Text("Plot: ${currentMovie.plot}")
            }
        }
        if (showBackButton) {
            BackToMainButton(navController)
        }
    }
}