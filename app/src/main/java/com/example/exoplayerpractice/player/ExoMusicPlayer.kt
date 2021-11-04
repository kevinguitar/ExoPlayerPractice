package com.example.exoplayerpractice.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.exoplayerpractice.data.Playlist
import com.example.exoplayerpractice.data.Track
import com.example.exoplayerpractice.playback.PlaybackNotificationManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoMusicPlayer @Inject constructor(
    private val player: ExoPlayer,
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

    private val globalListener = object : Player.Listener {

        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_BUFFERING -> _playbackState.value = loadingState
                Player.STATE_READY -> updatePlayerState()
                Player.STATE_IDLE, Player.STATE_ENDED -> Unit
            }
        }

        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
            updatePlayerState()
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            updatePlayerState()
        }

        private fun updatePlayerState() {
            _playbackState.value = if (player.playWhenReady) {
                notificationManager.showNotification()
                player.setupPlaybackProgressTimer()
                playingState
            } else {
                durationTimerJob?.cancel()
                pauseState
            }
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            _repeatMode.value = repeatMode
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            _shuffleModeEnabled.value = shuffleModeEnabled
        }
    }

    init {
        player.addListener(globalListener)
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
        player.seekToPreviousMediaItem()
    }

    override fun next() {
        player.seekToNextMediaItem()
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


    /**
     *  Playback states creation
     */
    private val loadingState get() = PlaybackState.Loading(currentPlaylistId, currentTrackId)
    private val pauseState get() = PlaybackState.Pause(currentPlaylistId, currentTrackId)
    private val playingState get() = PlaybackState.Playing(currentPlaylistId, currentTrackId)


    /**
     *  Playback timer
     */
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