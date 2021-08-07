package com.example.exoplayerpractice.data

data class Playlist(
    val id: String,
    val tracks: List<Track>
)

data class Track(
    val id: String,
    val title: String,
    val url: String
)