package com.example.exoplayerpractice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exoplayerpractice.data.Track
import com.example.exoplayerpractice.player.MusicPlayer
import com.example.exoplayerpractice.player.PlaybackState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TrackViewModel @AssistedInject constructor(
    @Assisted val track: Track,
    musicPlayer: MusicPlayer
) : ViewModel() {

    val isPlaying = musicPlayer.playbackState
        .map { state ->
            state !is PlaybackState.Pause && state.trackId == track.id
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    @AssistedFactory
    interface Factory {

        fun create(track: Track): TrackViewModel
    }
}