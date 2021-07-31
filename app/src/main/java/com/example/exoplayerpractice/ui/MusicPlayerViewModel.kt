package com.example.exoplayerpractice.ui

import androidx.lifecycle.ViewModel
import com.example.exoplayerpractice.R
import com.example.exoplayerpractice.player.MusicPlayer
import com.example.exoplayerpractice.player.PlaybackState
import com.google.android.exoplayer2.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    val musicPlayer: MusicPlayer
) : ViewModel() {

    val playRes = musicPlayer.playbackState.map { state ->
        when (state) {
            PlaybackState.Pause -> R.drawable.exo_icon_play
            PlaybackState.Playing -> R.drawable.exo_icon_pause
            PlaybackState.Loading -> R.drawable.exo_ic_forward
        }
    }

    val repeatRes = musicPlayer.repeatMode.map { mode ->
        when (mode) {
            Player.REPEAT_MODE_OFF -> R.drawable.exo_icon_repeat_off
            Player.REPEAT_MODE_ALL -> R.drawable.exo_icon_repeat_all
            Player.REPEAT_MODE_ONE -> R.drawable.exo_icon_repeat_one
            else -> error("not supported mode $mode")
        }
    }

    fun onPlayButtonClicked() {
        when (musicPlayer.playbackState.value) {
            PlaybackState.Pause -> musicPlayer.play()
            PlaybackState.Playing -> musicPlayer.pause()
            PlaybackState.Loading -> Unit
        }
    }
}