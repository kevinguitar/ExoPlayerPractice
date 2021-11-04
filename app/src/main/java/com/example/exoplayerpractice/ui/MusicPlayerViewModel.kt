package com.example.exoplayerpractice.ui

import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.example.exoplayerpractice.R
import com.example.exoplayerpractice.data.playlists
import com.example.exoplayerpractice.player.MusicPlayer
import com.example.exoplayerpractice.player.PlaybackState
import com.example.exoplayerpractice.utils.mapState
import com.example.exoplayerpractice.utils.toPlaybackTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    val musicPlayer: MusicPlayer,
    val adapter: MusicPlayerAdapter,
    private val playlistFactory: PlaylistViewModel.Factory,
    private val trackFactory: TrackViewModel.Factory
) : ViewModel() {

    val playRes = musicPlayer.playbackState
        .mapState { state ->
            when (state) {
                is PlaybackState.Pause -> R.drawable.exo_icon_play
                is PlaybackState.Playing -> R.drawable.exo_icon_pause
                is PlaybackState.Loading -> R.drawable.exo_ic_forward
            }
        }

    val repeatRes = musicPlayer.repeatMode
        .mapState { mode ->
            when (mode) {
                Player.REPEAT_MODE_OFF -> R.drawable.exo_icon_repeat_off
                Player.REPEAT_MODE_ALL -> R.drawable.exo_icon_repeat_all
                Player.REPEAT_MODE_ONE -> R.drawable.exo_icon_repeat_one
                else -> error("not supported mode $mode")
            }
        }

    val playbackTime = musicPlayer.playbackProgress
        .mapState { progress -> progress.current.toPlaybackTime }

    val duration = musicPlayer.playbackProgress
        .mapState { progress -> progress.duration.toPlaybackTime }

    val maxProgress = musicPlayer.playbackProgress
        .mapState { progress -> progress.duration.toInt() }

    private val isUserSeeking = MutableStateFlow(false)

    val playbackProgress = combine(musicPlayer.playbackProgress, isUserSeeking) { progress, seeking ->
        progress.current.toInt().takeUnless { seeking }
    }
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val onPlaybackSeekListener = object : SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) = Unit

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            isUserSeeking.value = true
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            isUserSeeking.value = false
            seekBar ?: return
            musicPlayer.seekTo(seekBar.progress.toLong())
        }
    }

    init {
        adapter.submitList(
            playlists.flatMap { playlist ->
                val playlistModel = listOf(playlistFactory.create(playlist))
                val tracksModel = playlist.tracks.map { track ->
                    trackFactory.create(playlist, track)
                }
                playlistModel + tracksModel
            }
        )
    }

    fun onPlayButtonClicked(view: View) {
        when (val state = musicPlayer.playbackState.value) {
            is PlaybackState.Pause -> if (state.playlistId == null) {
                Toast.makeText(
                    view.context,
                    "No playlist in player, pick a playlist first!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                musicPlayer.resume()
            }
            is PlaybackState.Playing -> musicPlayer.pause()
            is PlaybackState.Loading -> Unit
        }
    }

    override fun onCleared() {
        musicPlayer.clear()
        super.onCleared()
    }
}