package com.example.exoplayerpractice.ui

import androidx.lifecycle.ViewModel
import com.example.exoplayerpractice.data.Playlist
import com.example.exoplayerpractice.data.Track
import com.example.exoplayerpractice.player.MusicPlayer
import com.example.exoplayerpractice.player.PlaybackState
import com.example.exoplayerpractice.utils.mapState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class TrackViewModel @AssistedInject constructor(
    @Assisted private val playlist: Playlist,
    @Assisted val track: Track,
    private val musicPlayer: MusicPlayer
) : ViewModel() {

    val isPlaying = musicPlayer.playbackState
        .mapState { state ->
            state !is PlaybackState.Pause && state.trackId == track.id
        }

    fun playTrack() {
        musicPlayer.prepareAndPlay(playlist, track)
    }

    @AssistedFactory
    interface Factory {

        fun create(playlist: Playlist, track: Track): TrackViewModel
    }
}