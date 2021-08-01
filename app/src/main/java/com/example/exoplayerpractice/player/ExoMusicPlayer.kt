package com.example.exoplayerpractice.player

import com.google.android.exoplayer2.MediaItem
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

    init {
        player.addListener(object : Player.Listener {

            override fun onPlaybackStateChanged(state: Int) {
                val newState = when (state) {
                    Player.STATE_IDLE, Player.STATE_ENDED -> PlaybackState.Pause
                    Player.STATE_BUFFERING -> PlaybackState.Loading
                    else -> return
                }
                _playbackState.value = newState
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _playbackState.value = if (isPlaying) {
                    PlaybackState.Playing
                } else {
                    PlaybackState.Pause
                }
            }
        })
    }

    override fun play(newPlaylist: Playlist) {
        if (newPlaylist != playlist.value) {
            player.addMediaItems(newPlaylist.map { MediaItem.fromUri(it.url) })
            player.prepare()
        }
        player.playWhenReady = true
        player.play()
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