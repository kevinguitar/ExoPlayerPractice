package com.example.exoplayerpractice.player

import com.example.exoplayerpractice.data.Playlist
import kotlinx.coroutines.flow.StateFlow

interface MusicPlayer : MusicPlayerState, MusicPlayerControl

interface MusicPlayerState {

    val playbackState: StateFlow<PlaybackState>

    val repeatMode: StateFlow<Int>

    val shuffleModeEnabled: StateFlow<Boolean>
}

interface MusicPlayerControl {

    fun prepareAndPlay(playlist: Playlist)

    fun pause()

    fun resume()

    fun previous()

    fun next()

    fun toggleRepeatMode()

    fun toggleShuffleMode()

    fun clear()
}