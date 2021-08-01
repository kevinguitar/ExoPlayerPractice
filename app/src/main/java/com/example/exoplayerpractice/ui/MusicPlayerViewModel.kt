package com.example.exoplayerpractice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exoplayerpractice.R
import com.example.exoplayerpractice.data.freeTracks
import com.example.exoplayerpractice.player.MusicPlayer
import com.example.exoplayerpractice.player.PlaybackState
import com.google.android.exoplayer2.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    val musicPlayer: MusicPlayer,
    val adapter: TracksAdapter,
    private val trackFactory: TrackViewModel.Factory
) : ViewModel() {

    val playRes = musicPlayer.playbackState
        .map { state ->
            when (state) {
                PlaybackState.Pause -> R.drawable.exo_icon_play
                PlaybackState.Playing -> R.drawable.exo_icon_pause
                PlaybackState.Loading -> R.drawable.exo_ic_forward
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, R.drawable.exo_icon_play)

    val repeatRes = musicPlayer.repeatMode
        .map { mode ->
            when (mode) {
                Player.REPEAT_MODE_OFF -> R.drawable.exo_icon_repeat_off
                Player.REPEAT_MODE_ALL -> R.drawable.exo_icon_repeat_all
                Player.REPEAT_MODE_ONE -> R.drawable.exo_icon_repeat_one
                else -> error("not supported mode $mode")
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, R.drawable.exo_icon_repeat_off)

    init {
        adapter.submitList(freeTracks.map(trackFactory::create))
    }

    fun onPlayButtonClicked() {
        when (musicPlayer.playbackState.value) {
            PlaybackState.Pause -> musicPlayer.play(freeTracks)
            PlaybackState.Playing -> musicPlayer.pause()
            PlaybackState.Loading -> Unit
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicPlayer.clear()
    }
}