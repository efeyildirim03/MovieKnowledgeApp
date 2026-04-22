package com.example.movieknowledgeapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieknowledgeapp.data.Movie
import com.example.movieknowledgeapp.network.OmdbApiService
import com.example.movieknowledgeapp.components.BackToMainButton

@Composable
fun SearchByTitleScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showBackButton by rememberSaveable { mutableStateOf(true) }
    fun searchMovieByTitle() {
        if (title.isBlank()) {
            errorMessage = "Please enter a movie title"
            return
        }
        isLoading = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Search Movie Titles") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { searchMovieByTitle() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && title.isNotBlank()
        ) {
            Text(if (isLoading) "Searching..." else "Search")
        }

        if (isLoading) {
            LaunchedEffect(title) {
                try {
                    val result = OmdbApiService.searchMoviesByTitle(title)
                    if (result.isEmpty()) {
                        errorMessage = "No movies found for '$title'"
                    } else {
                        movies = result
                    }
                } catch (e: Exception) {
                    errorMessage = "Search failed: ${e.localizedMessage}"
                } finally {
                    isLoading = false
                }
            }
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies) { movie ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(movie.title, fontWeight = FontWeight.Bold)
                    Text("Year: ${movie.year}")

                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }

        if (showBackButton) {
            BackToMainButton(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchByTitleScreenPreview() {
    SearchByTitleScreen(navController = rememberNavController())
}