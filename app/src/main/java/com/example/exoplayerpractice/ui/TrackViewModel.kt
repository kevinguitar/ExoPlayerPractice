package com.example.exoplayerpractice.ui

import com.example.exoplayerpractice.data.Track
import com.example.exoplayerpractice.player.MusicPlayer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class TrackViewModel @AssistedInject constructor(
    @Assisted val track: Track,
    private val musicPlayer: MusicPlayer
) {

    val isPlaying: Boolean = false


    @AssistedFactory
    interface Factory {

        fun create(track: Track): TrackViewModel
    }
}