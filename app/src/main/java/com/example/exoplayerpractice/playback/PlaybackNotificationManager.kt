package com.example.exoplayerpractice.playback

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.PlayerNotificationManager
import com.example.exoplayerpractice.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val PLAYBACK_NOTIFICATION_ID = 0xb339

@SuppressLint("UnsafeOptInUsageError")
@Singleton
class PlaybackNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val player: ExoPlayer,
    mediaMetadataAdapter: MediaMetadataAdapter,
) {

    private val notificationChannelId: String
        get() = context.getString(R.string.playback_notification_channel)

    private val notificationManager = PlayerNotificationManager.Builder(
        context,
        PLAYBACK_NOTIFICATION_ID,
        notificationChannelId
    )
        .setMediaDescriptionAdapter(mediaMetadataAdapter)
        .setSmallIconResourceId(R.drawable.exo_icon_vr)
        // Disable rewind and fast forward actions
        .setRewindActionIconResourceId(0)
        .setFastForwardActionIconResourceId(0)
        .build()

    private val mediaSession = MediaSession.Builder(context, player).build()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManagerCompat.from(context).createNotificationChannel(
                NotificationChannel(
                    notificationChannelId,
                    notificationChannelId,
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        }
    }

    fun showNotification() {
        notificationManager.setMediaSessionToken(mediaSession.sessionCompatToken as MediaSessionCompat.Token)
        notificationManager.setPlayer(player)
    }

    fun hideNotification() {
        notificationManager.setPlayer(null)
        mediaSession.release()
    }

}