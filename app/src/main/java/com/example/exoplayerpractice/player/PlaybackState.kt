package com.example.exoplayerpractice.player

sealed class PlaybackState(
    open val playlistId: String?,
    open val trackId: String?
) {

    data class Pause(
        override val playlistId: String? = null,
        override val trackId: String? = null
    ) : PlaybackState(playlistId, trackId)

    data class Playing(
        override val playlistId: String?,
        override val trackId: String?
    ) : PlaybackState(playlistId, trackId)

    data class Loading(
        override val playlistId: String?,
        override val trackId: String?
    ) : PlaybackState(playlistId, trackId)

}