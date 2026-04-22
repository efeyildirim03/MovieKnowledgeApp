package com.example.movieknowledgeapp.network

import com.example.movieknowledgeapp.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object OmdbApiService {
    private const val API_KEY = "f7b9c571" // Consider moving to buildConfig
    private const val BASE_URL = "https://www.omdbapi.com/"
    private const val SEARCH_LIMIT = 10 // Meets requirement of at least 10 movies

    suspend fun fetchMovieDetails(title: String): Movie? = withContext(Dispatchers.IO) {
        try {
            val url = "$BASE_URL?t=${title.encodeURL()}&apikey=$API_KEY"
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val data = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(data)

                if (json.optString("Response") == "True") {
                    Movie(
                        imdbID = json.getString("imdbID"),
                        title = json.getString("Title"),
                        year = json.getString("Year"),
                        rated = json.getString("Rated"),
                        released = json.getString("Released"),
                        runtime = json.getString("Runtime"),
                        genre = json.getString("Genre"),
                        director = json.getString("Director"),
                        writer = json.getString("Writer"),
                        actors = json.getString("Actors"),
                        plot = json.getString("Plot"),
                        // Add additional fields as needed
                    )
                } else null
            } else null
        } catch (e: Exception) {
            null // Or better error handling
        }
    }

    suspend fun searchMoviesByTitle(query: String): List<Movie> = withContext(Dispatchers.IO) {
        try {
            val url = "$BASE_URL?s=${query.encodeURL()}&apikey=$API_KEY"
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val data = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(data)

                if (json.optString("Response") == "True") {
                    val searchResults = json.getJSONArray("Search")
                    val movies = mutableListOf<Movie>()

                    // Limit to SEARCH_LIMIT results as per requirements
                    for (i in 0 until minOf(searchResults.length(), SEARCH_LIMIT)) {
                        val item = searchResults.getJSONObject(i)
                        // Use basic info from search result instead of fetching each movie
                        movies.add(
                            Movie(
                                imdbID = item.getString("imdbID"),
                                title = item.getString("Title"),
                                year = item.getString("Year"),
                                rated = "N/A", // Not available in search results
                                released = "N/A",
                                runtime = "N/A",
                                genre = "N/A",
                                director = "N/A",
                                writer = "N/A",
                                actors = "N/A",
                                plot = "N/A"
                            )
                        )
                    }
                    movies
                } else emptyList()
            } else emptyList()
        } catch (e: Exception) {
            emptyList() // Or better error handling
        }
    }

    private fun String.encodeURL(): String =
        URLEncoder.encode(this, "UTF-8")
}