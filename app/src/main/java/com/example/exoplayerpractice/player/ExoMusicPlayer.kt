package com.example.exoplayerpractice.player

import com.example.exoplayerpractice.data.Playlist
import com.example.exoplayerpractice.data.Track
import com.example.exoplayerpractice.playback.PlaybackNotificationManager
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoMusicPlayer @Inject constructor(
    private val player: SimpleExoPlayer,
    private val notificationManager: PlaybackNotificationManager
) : MusicPlayer {

    private var currentPlaylistId: String? = null

    private val currentTrackId: String?
        get() = player.currentMediaItem?.mediaId

    private val _playbackState = MutableStateFlow<PlaybackState>(PlaybackState.Pause())
    override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private val _playbackProgress = MutableStateFlow(PlaybackProgress(0, 0))
    override val playbackProgress: StateFlow<PlaybackProgress> = _playbackProgress.asStateFlow()

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
        Player.EVENT_PLAYBACK_STATE_CHANGED in events
                || Player.EVENT_PLAY_WHEN_READY_CHANGED in events
                || Player.EVENT_IS_LOADING_CHANGED in events -> {

            _playbackState.value = when {
                player.isPlaying -> PlaybackState.Playing(currentPlaylistId, currentTrackId)
                player.isLoading -> PlaybackState.Loading(currentPlaylistId, currentTrackId)
                else -> {
                    PlaybackState.Pause(currentPlaylistId, currentTrackId)
                }
            }

            notificationManager.showNotification()
            player.setupPlaybackProgressTimer()
        }

        Player.EVENT_REPEAT_MODE_CHANGED in events -> {
            _repeatMode.value = player.repeatMode
        }

        Player.EVENT_SHUFFLE_MODE_ENABLED_CHANGED in events -> {
            _shuffleModeEnabled.value = player.shuffleModeEnabled
        }

        else -> Unit
    }

    override fun prepareAndPlay(playlist: Playlist, track: Track?) {
        if (currentPlaylistId != playlist.id) {
            currentPlaylistId = playlist.id
            player.setMediaItems(
                playlist.tracks
                    .map { it.toMediaItem() }
            )
            player.prepare()
        }
        if (track != null) {
            val position = playlist.tracks.indexOf(track)
            player.seekToDefaultPosition(position)
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
        player.seekToPreviousWindow()
    }

    override fun next() {
        player.seekToNextWindow()
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }

    override fun toggleRepeatMode() {
        val repeatMode = when (_repeatMode.value) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
        player.repeatMode = repeatMode
    }

    override fun toggleShuffleMode() {
        val shuffleMode = !_shuffleModeEnabled.value
        player.shuffleModeEnabled = shuffleMode
    }

    override fun clear() {
        currentPlaylistId = null
        player.clearMediaItems()
        notificationManager.hideNotification()
    }

    private var durationTimerJob: Job? = null

    private fun Player.setupPlaybackProgressTimer() {
        durationTimerJob?.cancel()
        durationTimerJob = GlobalScope.launch {
            while (true) {
                withContext(Dispatchers.Main) {
                    _playbackProgress.value = PlaybackProgress(
                        current = currentPosition,
                        duration = duration.coerceAtLeast(0)
                    )
                }
                delay(100)
            }
        }
    }
}