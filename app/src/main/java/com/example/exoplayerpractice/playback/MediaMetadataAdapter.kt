package com.example.exoplayerpractice.playback

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.media3.common.Player
import androidx.media3.session.PlayerNotificationManager
import androidx.media3.session.PlayerNotificationManager.MediaDescriptionAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.exoplayerpractice.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val NOTIFICATION_LARGE_ICON_SIZE = 144 // px

class MediaMetadataAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaDescriptionAdapter {

    private var currentArtworkUri: Uri? = null
    private var currentBitmap: Bitmap? = null

    override fun getCurrentContentTitle(player: Player): CharSequence {
        return player.mediaMetadata.displayTitle ?: ""
    }

    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        return null
    }

    override fun getCurrentContentText(player: Player): CharSequence? {
        return player.mediaMetadata.description
    }

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        val artworkUri = player.mediaMetadata.artworkUri ?: return null

        // Same media item, just return cached bitmap
        if (artworkUri == currentArtworkUri && currentBitmap != null) {
            return currentBitmap
        }

        // Cache the bitmap for the current song so that successive calls to
        // `getCurrentLargeIcon` don't cause the bitmap to be recreated.
        currentArtworkUri = artworkUri

        GlobalScope.launch {
            val bitmap = resolveUriAsBitmap(artworkUri)

            currentBitmap = bitmap
            callback.onBitmap(bitmap)
        }
        return null
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun resolveUriAsBitmap(uri: Uri): Bitmap {
        return withContext(Dispatchers.IO) {
            // Block on downloading artwork.
            Glide.with(context)
                .applyDefaultRequestOptions(glideOptions)
                .asBitmap()
                .load(uri)
                .submit(NOTIFICATION_LARGE_ICON_SIZE, NOTIFICATION_LARGE_ICON_SIZE)
                .get()
        }
    }

    private val glideOptions = RequestOptions()
        .fallback(R.drawable.default_art)
        .diskCacheStrategy(DiskCacheStrategy.DATA)

}