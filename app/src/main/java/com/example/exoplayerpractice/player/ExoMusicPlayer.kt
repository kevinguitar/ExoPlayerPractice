package com.example.exoplayerpractice.player

import com.example.exoplayerpractice.data.Track
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoMusicPlayer @Inject constructor(
    private val player: SimpleExoPlayer
) : MusicPlayer {

    private val _playlist = MutableStateFlow<Playlist>(emptyList())
    override val playlist: StateFlow<Playlist> get() = _playlist

    private val _playbackState = MutableStateFlow(PlaybackState.Pause)
    override val playbackState: StateFlow<PlaybackState> get() = _playbackState

    private val _repeatMode = MutableStateFlow(Player.REPEAT_MODE_OFF)
    override val repeatMode: StateFlow<Int> get() = _repeatMode

    private val _shuffleModeEnabled = MutableStateFlow(false)
    override val shuffleModeEnabled: StateFlow<Boolean> get() = _shuffleModeEnabled

    override fun play(track: Track, playlist: Playlist) {
        // TODO: 2021/7/31
    }

    override fun pause() {
        player.pause()
    }

    override fun previous() {
        player.previous()
    }

    override fun next() {
        player.next()
    }

    override fun toggleRepeatMode() {
        val repeatMode = when (_repeatMode.value) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
        _repeatMode.value = repeatMode
        player.repeatMode = repeatMode
    }

    override fun toggleShuffleMode() {
        val shuffleMode = !_shuffleModeEnabled.value
        _shuffleModeEnabled.value = shuffleMode
        player.shuffleModeEnabled = shuffleMode
    }

    override fun clear() {
        player.clearMediaItems()
    }

}