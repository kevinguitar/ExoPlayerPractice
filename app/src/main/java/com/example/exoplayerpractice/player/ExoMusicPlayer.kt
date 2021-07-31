package com.example.exoplayerpractice.player

import com.example.exoplayerpractice.data.Track
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_OFF
import com.google.android.exoplayer2.Player.RepeatMode
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

    @RepeatMode
    override var repeatMode: Int = REPEAT_MODE_OFF
        set(value) {
            field = value
            player.repeatMode = value
        }

    override var shuffleModeEnabled: Boolean = false
        set(value) {
            field = value
            player.shuffleModeEnabled = value
        }

    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun addTrack(track: Track) {
        player.addMediaItem(MediaItem.fromUri(track.url))
    }

    override fun removeTrack(track: Track) {

    }

    override fun clear() {
        player.clearMediaItems()
    }

}