package com.example.exoplayerpractice.player

import com.example.exoplayerpractice.data.Playlist
import com.example.exoplayerpractice.data.Track
import com.example.exoplayerpractice.player.PlaybackState.*
import kotlinx.coroutines.flow.StateFlow

interface MusicPlayer : MusicPlayerState, MusicPlayerControl

interface MusicPlayerState {

    /**
     *  [PlaybackState] indicate player is in [Pause], [Playing] or [Loading], all the state
     *  contains playlist/ track id to let observer act accordingly.
     */
    val playbackState: StateFlow<PlaybackState>

    val playbackProgress: StateFlow<PlaybackProgress>

    val repeatMode: StateFlow<Int>

    val shuffleModeEnabled: StateFlow<Boolean>
}

interface MusicPlayerControl {

    /**
     *  Prepare a playlist in ExoPlayer and play immediately right after prepared.
     *
     *  @param  track   The track that playback should start with, start from beginning if not set.
     */
    fun prepareAndPlay(playlist: Playlist, track: Track? = null)

    fun pause()

    fun resume()

    fun previous()

    fun next()

    /**
     *  @param position Playback position in millisecond.
     */
    fun seekTo(position: Long)

    fun toggleRepeatMode()

    fun toggleShuffleMode()

    fun clear()
}