package com.example.exoplayerpractice.ui

import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exoplayerpractice.R
import com.example.exoplayerpractice.data.playlists
import com.example.exoplayerpractice.player.MusicPlayer
import com.example.exoplayerpractice.player.PlaybackState
import com.example.exoplayerpractice.utils.toPlaybackTime
import com.google.android.exoplayer2.Player
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
        .map { state ->
            when (state) {
                is PlaybackState.Pause -> R.drawable.exo_icon_play
                is PlaybackState.Playing -> R.drawable.exo_icon_pause
                is PlaybackState.Loading -> R.drawable.exo_ic_forward
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

    val playbackTime = musicPlayer.playbackProgress
        .map { progress -> progress.current.toPlaybackTime }
        .stateIn(viewModelScope, SharingStarted.Lazily, "0:00")

    val duration = musicPlayer.playbackProgress
        .map { progress -> progress.duration.toPlaybackTime }
        .stateIn(viewModelScope, SharingStarted.Lazily, "0:00")

    val maxProgress = musicPlayer.playbackProgress
        .map { progress -> progress.duration.toInt() }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    private val isUserSeeking = MutableStateFlow(false)

    val playbackProgress = combine(musicPlayer.playbackProgress, isUserSeeking) { progress, seeking ->
        progress.current.toInt().takeUnless { seeking }
    }
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

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
                val tracksModel = playlist.tracks.map(trackFactory::create)
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
        super.onCleared()
        musicPlayer.clear()
    }
}