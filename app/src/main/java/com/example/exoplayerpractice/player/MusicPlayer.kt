package com.example.exoplayerpractice.player

import kotlinx.coroutines.flow.StateFlow

interface MusicPlayer : MusicPlayerState, MusicPlayerControl

interface MusicPlayerState {

    val playlist: StateFlow<Playlist>

    val playbackState: StateFlow<PlaybackState>

    val repeatMode: StateFlow<Int>

    val shuffleModeEnabled: StateFlow<Boolean>
}

interface MusicPlayerControl {

    fun play(newPlaylist: Playlist)

    fun pause()

    fun previous()

    fun next()

    fun toggleRepeatMode()

    fun toggleShuffleMode()

    fun clear()
}