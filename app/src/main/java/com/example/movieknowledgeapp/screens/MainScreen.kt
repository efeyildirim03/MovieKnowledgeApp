package com.example.movieknowledgeapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Movie Knowledge App",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = { navController.navigate("addMovies") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Movies to DB", fontSize = 18.sp)
            }

            Button(
                onClick = { navController.navigate("searchMovie") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Search for Movies", fontSize = 18.sp)
            }

            Button(
                onClick = { navController.navigate("searchActors") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Search for Actors", fontSize = 18.sp)
            }

            Button(
                onClick = { navController.navigate("searchByTitle") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Search Titles (Web API)", fontSize = 18.sp)
            }
        }
    }
}
