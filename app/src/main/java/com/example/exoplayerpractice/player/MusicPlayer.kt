package com.example.exoplayerpractice.player

import com.example.exoplayerpractice.data.Track
import com.google.android.exoplayer2.Player.RepeatMode
import kotlinx.coroutines.flow.StateFlow

interface MusicPlayer {

    val playlist: StateFlow<Playlist>

    @RepeatMode
    var repeatMode: Int

    var shuffleModeEnabled: Boolean

    fun play()

    fun pause()

    fun addTrack(track: Track)

    fun removeTrack(track: Track)

    fun clear()
}