package com.example.movieknowledgeapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieknowledgeapp.components.BackToMainButton
import com.example.movieknowledgeapp.data.Movie
import com.example.movieknowledgeapp.data.MovieDatabase
import kotlinx.coroutines.launch

@Composable
fun SearchActorScreen(navController: NavController) {
    var actorName by remember { mutableStateOf("") }
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = remember { MovieDatabase.getDatabase(context) }
    val movieDao = remember { database.movieDao() }
    var showBackButton by rememberSaveable { mutableStateOf(true) }
    // Function to search movies by actor
    fun searchMoviesByActor() {
        if (actorName.isBlank()) {
            errorMessage = "Please enter an actor name"
            return
        }

        scope.launch {
            isLoading = true
            errorMessage = null
            try {
                movies = movieDao.getMoviesByActor(actorName)
                if (movies.isEmpty()) {
                    errorMessage = "No movies found for actor: $actorName"
                }
            } catch (e: Exception) {
                errorMessage = "Search failed: ${e.localizedMessage}"
                movies = emptyList()
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search Section
        TextField(
            value = actorName,
            onValueChange = { actorName = it },
            label = { Text("Actor Name (partial or full)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { searchMoviesByActor() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && actorName.isNotBlank()
        ) {
            Text(if (isLoading) "Searching..." else "Search Actor")
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

        // Results Section
        if (movies.isNotEmpty()) {
            Text(
                text = "Found ${movies.size} movie(s):",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies) { movie ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text("Title: ${movie.title}", fontWeight = FontWeight.Bold)
                        Text("Year: ${movie.year}")
                        Text("Actors: ${movie.actors}")
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
        if (showBackButton) {
            BackToMainButton(navController)
        }
    }
}