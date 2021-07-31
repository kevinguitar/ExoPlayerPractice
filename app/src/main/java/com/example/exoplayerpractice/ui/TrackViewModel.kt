package com.example.exoplayerpractice.ui

import androidx.lifecycle.ViewModel
import com.example.exoplayerpractice.data.Track
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class TrackViewModel @AssistedInject constructor(
    @Assisted val track: Track,
) : ViewModel() {

    val isPlaying: Boolean = false


    @AssistedFactory
    interface Factory {

        fun create(track: Track): TrackViewModel
    }
}