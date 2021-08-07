package com.example.exoplayerpractice.player

sealed class PlaybackState {

    object Pause : PlaybackState()

    data class Playing(
        val playlistId: String,
        val trackId: String
    ) : PlaybackState()

    data class Loading(val trackId: String) : PlaybackState()
}