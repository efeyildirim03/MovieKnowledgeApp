package com.example.movieknowledgeapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieknowledgeapp.data.Movie
import com.example.movieknowledgeapp.data.MovieDatabase
import com.example.movieknowledgeapp.network.OmdbApiService
import kotlinx.coroutines.launch
import com.example.movieknowledgeapp.components.BackToMainButton

@Composable
fun AddMoviesScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = remember { MovieDatabase.getDatabase(context) }
    val movieDao = remember { database.movieDao() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            // New button to add hardcoded movies to DB
            Button(
                onClick = {
                    scope.launch {
                        val hardcodedMovies = listOf(
                            Movie("tt0133093", "The Matrix", "1999", "R", "31 Mar 1999", "136 min", "Action, Sci-Fi", "Lana Wachowski, Lilly Wachowski", "Lilly Wachowski, Lana Wachowski", "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss", "A computer hacker learns about the true nature of his reality."),
                            Movie("tt1375666", "Inception", "2010", "PG-13", "16 Jul 2010", "148 min", "Action, Adventure, Sci-Fi", "Christopher Nolan", "Christopher Nolan", "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page", "A thief who steals corporate secrets through dream-sharing technology."),
                            Movie("tt0114369", "Se7en", "1995", "R", "22 Sep 1995", "127 min", "Crime, Drama, Mystery", "David Fincher", "Andrew Kevin Walker", "Brad Pitt, Morgan Freeman, Gwyneth Paltrow", "Two detectives hunt a serial killer who uses the seven deadly sins."),
                            Movie("tt0133093", "The Matrix", "1999", "R", "31 Mar 1999", "136 min", "Action, Sci-Fi", "Lana Wachowski, Lilly Wachowski", "Lilly Wachowski, Lana Wachowski", "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss", "A computer hacker learns about the true nature of his reality."),
                            Movie("tt1375666", "Inception", "2010", "PG-13", "16 Jul 2010", "148 min", "Action, Adventure, Sci-Fi", "Christopher Nolan", "Christopher Nolan", "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page", "A thief who steals corporate secrets through dream-sharing technology."),
                            Movie("tt0114369", "Se7en", "1995", "R", "22 Sep 1995", "127 min", "Crime, Drama, Mystery", "David Fincher", "Andrew Kevin Walker", "Brad Pitt, Morgan Freeman, Gwyneth Paltrow", "Two detectives hunt a serial killer who uses the seven deadly sins."),
                            Movie("tt0120737", "The Lord of the Rings: The Fellowship of the Ring", "2001", "PG-13", "19 Dec 2001", "178 min", "Adventure, Fantasy", "Peter Jackson", "J.R.R. Tolkien, Fran Walsh", "Elijah Wood, Ian McKellen, Orlando Bloom", "A meek Hobbit begins a quest to destroy a powerful ring."),
                            Movie("tt0468569", "The Dark Knight", "2008", "PG-13", "18 Jul 2008", "152 min", "Action, Crime, Drama", "Christopher Nolan", "Jonathan Nolan, Christopher Nolan", "Christian Bale, Heath Ledger, Aaron Eckhart", "Batman faces his greatest challenge when the Joker unleashes chaos."),
                            Movie("tt0110357", "The Lion King", "1994", "G", "24 Jun 1994", "88 min", "Animation, Adventure, Drama", "Roger Allers, Rob Minkoff", "Irene Mecchi, Jonathan Roberts", "Matthew Broderick, Jeremy Irons, James Earl Jones", "A young lion prince flees his kingdom only to learn his destiny."),
                            Movie("tt0109830", "Forrest Gump", "1994", "PG-13", "06 Jul 1994", "142 min", "Drama, Romance", "Robert Zemeckis", "Winston Groom, Eric Roth", "Tom Hanks, Robin Wright, Gary Sinise", "A man with low IQ accomplishes great things in his life."),
                            Movie("tt0167260", "The Lord of the Rings: The Return of the King", "2003", "PG-13", "17 Dec 2003", "201 min", "Adventure, Fantasy", "Peter Jackson", "J.R.R. Tolkien, Fran Walsh", "Elijah Wood, Viggo Mortensen, Ian McKellen", "Gandalf and Aragorn lead the World of Men against Sauron's army."),
                            Movie("tt0137523", "Fight Club", "1999", "R", "15 Oct 1999", "139 min", "Drama", "David Fincher", "Chuck Palahniuk, Jim Uhls", "Brad Pitt, Edward Norton, Meat Loaf", "An insomniac office worker forms an underground fight club."),
                            Movie("tt0110413", "Léon: The Professional", "1994", "R", "18 Nov 1994", "110 min", "Action, Crime, Drama", "Luc Besson", "Luc Besson", "Jean Reno, Gary Oldman, Natalie Portman", "A professional assassin takes in a 12-year-old girl after her family is murdered."),
                            Movie("tt0076759", "Star Wars: Episode IV - A New Hope", "1977", "PG", "25 May 1977", "121 min", "Action, Adventure, Fantasy", "George Lucas", "George Lucas", "Mark Hamill, Harrison Ford, Carrie Fisher", "Luke Skywalker joins forces with rebels to destroy the Death Star."),
                            Movie("tt0088763", "Back to the Future", "1985", "PG", "03 Jul 1985", "116 min", "Adventure, Comedy, Sci-Fi", "Robert Zemeckis", "Robert Zemeckis, Bob Gale", "Michael J. Fox, Christopher Lloyd, Lea Thompson", "A teenager is accidentally sent 30 years into the past."),
                            Movie("tt0114814", "The Usual Suspects", "1995", "R", "15 Sep 1995", "106 min", "Crime, Mystery, Thriller", "Bryan Singer", "Christopher McQuarrie", "Kevin Spacey, Gabriel Byrne, Chazz Palminteri", "A sole survivor tells the story of a group of criminals."),
                            Movie("tt0102926", "The Silence of the Lambs", "1991", "R", "14 Feb 1991", "118 min", "Crime, Drama, Thriller", "Jonathan Demme", "Thomas Harris, Ted Tally", "Jodie Foster, Anthony Hopkins, Lawrence A. Bonney", "A young FBI cadet seeks help from an imprisoned cannibal killer."),
                            Movie("tt0118799", "Life Is Beautiful", "1997", "PG-13", "12 Feb 1999", "116 min", "Comedy, Drama, Romance", "Roberto Benigni", "Vincenzo Cerami, Roberto Benigni", "Roberto Benigni, Nicoletta Braschi, Giorgio Cantarini", "A Jewish father uses humor to protect his son in a Nazi concentration camp."),
                            Movie("tt0120586", "American History X", "1998", "R", "30 Oct 1998", "119 min", "Drama", "Tony Kaye", "David McKenna", "Edward Norton, Edward Furlong, Beverly D'Angelo", "A former neo-nazi tries to prevent his brother from going down the same path."),
                            Movie("tt0245429", "Spirited Away", "2001", "PG", "20 Sep 2002", "125 min", "Animation, Adventure, Family", "Hayao Miyazaki", "Hayao Miyazaki", "Daveigh Chase, Suzanne Pleshette, Miyu Irino", "A young girl wanders into a world of gods and spirits."),
                            Movie("tt0082971", "Raiders of the Lost Ark", "1981", "PG", "12 Jun 1981", "115 min", "Action, Adventure", "Steven Spielberg", "Lawrence Kasdan, George Lucas", "Harrison Ford, Karen Allen, Paul Freeman", "Archaeologist Indiana Jones races to find the Ark of the Covenant."),
                            Movie("tt0112573", "Braveheart", "1995", "R", "24 May 1995", "178 min", "Biography, Drama, History", "Mel Gibson", "Randall Wallace", "Mel Gibson, Sophie Marceau, Patrick McGoohan", "Scottish warrior William Wallace leads his countrymen in a rebellion."),
                            Movie("tt0119217", "Good Will Hunting", "1997", "R", "09 Jan 1998", "126 min", "Drama, Romance", "Gus Van Sant", "Matt Damon, Ben Affleck", "Robin Williams, Matt Damon, Ben Affleck", "A janitor at MIT has a gift for mathematics.")
                        )
                        for (movie in hardcodedMovies) {
                            movieDao.insert(movie)
                        }
                        Toast.makeText(context, "Hardcoded movies added to DB", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Add Movies to DB")
            }
        }
        // Back to Main Menu button at bottom
        BackToMainButton(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun AddMoviesScreenPreview() {
    AddMoviesScreen(navController = rememberNavController())
}
