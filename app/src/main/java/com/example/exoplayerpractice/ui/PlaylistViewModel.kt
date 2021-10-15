package com.example.exoplayerpractice.ui

import androidx.lifecycle.ViewModel
import com.example.exoplayerpractice.data.Playlist
import com.example.exoplayerpractice.player.MusicPlayer
import com.example.exoplayerpractice.player.PlaybackState
import com.example.exoplayerpractice.utils.mapState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class PlaylistViewModel @AssistedInject constructor(
    @Assisted val playlist: Playlist,
    private val musicPlayer: MusicPlayer
) : ViewModel() {

    val isPlaying = musicPlayer.playbackState
        .mapState { state ->
            state !is PlaybackState.Pause && state.playlistId == playlist.id
        }

    fun play() {
        musicPlayer.prepareAndPlay(playlist)
    }

    @AssistedFactory
    interface Factory {

        fun create(playlist: Playlist): PlaylistViewModel
    }
}