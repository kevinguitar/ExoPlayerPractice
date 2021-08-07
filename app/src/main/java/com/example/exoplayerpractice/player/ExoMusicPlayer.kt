package com.example.exoplayerpractice.player

import com.example.exoplayerpractice.data.Playlist
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

    private var currentPlaylistId: String? = null

    private val currentTrackId: String?
        get() = player.currentMediaItem?.mediaId

    private val _playbackState = MutableStateFlow<PlaybackState>(PlaybackState.Pause)
    override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private val _repeatMode = MutableStateFlow(Player.REPEAT_MODE_OFF)
    override val repeatMode: StateFlow<Int> = _repeatMode.asStateFlow()

    private val _shuffleModeEnabled = MutableStateFlow(false)
    override val shuffleModeEnabled: StateFlow<Boolean> = _shuffleModeEnabled.asStateFlow()

    init {
        player.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                this@ExoMusicPlayer.onEvents(player, events)
            }
        })
    }

    private fun onEvents(player: Player, events: Player.Events) = when {
        Player.EVENT_PLAYBACK_STATE_CHANGED in events ||
                Player.EVENT_PLAY_WHEN_READY_CHANGED in events -> {
            val newState = when {
                player.isPlaying -> PlaybackState.Playing(currentPlaylistId!!, currentTrackId!!)
                player.playbackState == Player.STATE_BUFFERING -> PlaybackState.Loading(currentTrackId!!)
                else -> PlaybackState.Pause
            }
            _playbackState.value = newState
        }
        else -> Unit
    }

    override fun prepareAndPlay(playlist: Playlist) {
        if (currentPlaylistId != playlist.id) {
            currentPlaylistId = playlist.id
            player.setMediaItems(playlist.tracks.map {
                MediaItem.Builder()
                    .setUri(it.url)
                    .setMediaId(it.id)
                    .build()
            })
            player.prepare()
        }
        player.playWhenReady = true
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun resume() {
        player.play()
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
        currentPlaylistId = null
        player.clearMediaItems()
    }

}