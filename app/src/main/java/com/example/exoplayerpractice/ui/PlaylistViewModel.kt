package com.example.exoplayerpractice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exoplayerpractice.data.Playlist
import com.example.exoplayerpractice.player.MusicPlayer
import com.example.exoplayerpractice.player.PlaybackState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlaylistViewModel @AssistedInject constructor(
    @Assisted val playlist: Playlist,
    private val musicPlayer: MusicPlayer
) : ViewModel() {

    val isPlaying = musicPlayer.playbackState
        .map { state ->
            state is PlaybackState.Playing && state.playlistId == playlist.id
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun play() {
        musicPlayer.prepareAndPlay(playlist)
    }

    @AssistedFactory
    interface Factory {

        fun create(playlist: Playlist): PlaylistViewModel
    }
}