# Movie Knowledge App 🎬

An Android application built with Kotlin and Jetpack Compose that allows users to search, store, and explore movie information using the OMDB API and a local Room database.

---

## 📱 Features

- **Add Movies** — Search movies by title via OMDB API and save them to a local database
- **Search Movies** — Search through locally saved movies
- **Search by Actor** — Filter movies by actor name from the local database
- **Search Titles (Web API)** — Live search directly from OMDB API returning up to 10 results
- **Local Storage** — All saved movies are stored using Room (SQLite) for offline access

---

## 🛠 Tech Stack

| Technology | Usage |
|------------|-------|
| Kotlin | Primary language |
| Jetpack Compose | UI framework |
| Room (SQLite) | Local database |
| OMDB API | Movie data source |
| Navigation Component | Screen navigation |
| Coroutines | Async API calls |

---

## 📂 Project Structureapp/src/main/java/com/example/movieknowledgeapp/
├── data/
│   ├── Movie.kt
│   ├── MovieDao.kt
│   └── MovieDatabase.kt
├── network/
│   └── OmdbApiService.kt
├── screens/
│   ├── MainScreen.kt
│   ├── AddMoviesScreen.kt
│   ├── SearchMovieScreen.kt
│   ├── SearchActorScreen.kt
│   └── SearchByTitleScreen.kt
├── components/
│   └── BackToMainButton.kt
└── MainActivity.kt---

## 🔧 Setup & Installation

1. Clone the repository:
```bash
git clone https://github.com/efeyildirim03/MovieKnowledgeApp.git
```
2. Open in Android Studio
3. Connect a device or start an emulator
4. Press **Run ▶️**

---

## 🔑 API

This app uses the [OMDB API](https://www.omdbapi.com/) to fetch movie data.


---

## 📸 Screens

| Screen | Description |
|--------|-------------|
| Main Screen | Navigation hub with 4 options |
| Add Movies | Search OMDB and save to local DB |
| Search Movies | Browse locally saved movies |
| Search by Actor | Filter movies by actor name |
| Search Titles | Live search via OMDB API |
