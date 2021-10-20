package com.example.exoplayerpractice.playback

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.media2.session.MediaSession
import androidx.media2.session.SessionCommand
import androidx.media2.session.SessionCommandGroup
import com.example.exoplayerpractice.R
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.media2.SessionPlayerConnector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val PLAYBACK_NOTIFICATION_ID = 0xb339

@Singleton
class PlaybackNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val player: SimpleExoPlayer
) {

    private val notificationChannelId: String
        get() = context.getString(R.string.playback_notification_channel)

    private val notificationManager = PlayerNotificationManager.Builder(
        context,
        PLAYBACK_NOTIFICATION_ID,
        notificationChannelId
    )
        .setSmallIconResourceId(R.drawable.exo_icon_vr)
        // Disable rewind and fast forward actions
        .setRewindActionIconResourceId(0)
        .setFastForwardActionIconResourceId(0)
        .build()

    private val sessionPlayer = SessionPlayerConnector(player)

    private val mediaSessionCallback = object : MediaSession.SessionCallback() {
        override fun onConnect(
            session: MediaSession,
            controller: MediaSession.ControllerInfo
        ) = SessionCommandGroup.Builder()
            .addCommand(SessionCommand(SessionCommand.COMMAND_CODE_PLAYER_PLAY))
            .addCommand(SessionCommand(SessionCommand.COMMAND_CODE_PLAYER_PAUSE))
            .addCommand(SessionCommand(SessionCommand.COMMAND_CODE_PLAYER_SEEK_TO))
            .build()
    }

    private val mediaSession = MediaSession.Builder(context, sessionPlayer)
        .setSessionCallback(ContextCompat.getMainExecutor(context), mediaSessionCallback)
        .build()

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
        notificationManager.setMediaSessionToken(mediaSession.sessionCompatToken)
        notificationManager.setPlayer(player)
    }

    fun hideNotification() {
        notificationManager.setPlayer(null)
        mediaSession.close()
    }

}