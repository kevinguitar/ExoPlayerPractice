package com.example.exoplayerpractice.player

import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoMusicPlayer @Inject constructor(
    private val player: SimpleExoPlayer
) : MusicPlayer {

    private val _playlist = MutableStateFlow<Playlist>(emptyList())
    override val playlist: StateFlow<Playlist> = _playlist.asStateFlow()

    private val _playbackState = MutableStateFlow(PlaybackState.Pause)
    override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private val _repeatMode = MutableStateFlow(Player.REPEAT_MODE_OFF)
    override val repeatMode: StateFlow<Int> = _repeatMode.asStateFlow()

    private val _shuffleModeEnabled = MutableStateFlow(false)
    override val shuffleModeEnabled: StateFlow<Boolean> = _shuffleModeEnabled.asStateFlow()

    override fun play(playlist: Playlist) {
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