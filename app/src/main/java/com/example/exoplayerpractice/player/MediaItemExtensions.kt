package com.example.exoplayerpractice.player

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.exoplayerpractice.data.Track

fun Track.toMediaItem(): MediaItem {
    val metadata = MediaMetadata.Builder()
        .setTitle(title)
        .setArtist(url)
        .setArtworkUri(Uri.parse(artworkUrl))
        .build()

    return MediaItem.Builder()
        .setMediaMetadata(metadata)
        .setUri(url)
        .setMediaId(id)
        .build()
}