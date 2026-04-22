package com.example.movieknowledgeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    // Add this temporary function to your DAO
    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getExactCount(): Int

    // Insert a movie into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)
    // Get all movies from the database
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<Movie>>

    // Search movies by actor name (case-insensitive match)
    @Query("SELECT * FROM movies WHERE LOWER(actors) LIKE '%' || LOWER(:actorName) || '%'")
    suspend fun getMoviesByActor(actorName: String): List<Movie>

    // Search movies by title (case-insensitive match)
    @Query("SELECT * FROM movies WHERE LOWER(title) LIKE '%' || LOWER(:title) || '%'")
    suspend fun getMoviesByTitle(title: String): List<Movie>
}
