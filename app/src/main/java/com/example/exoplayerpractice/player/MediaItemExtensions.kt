package com.example.exoplayerpractice.player

import com.example.exoplayerpractice.data.Track
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata

fun Track.toMediaItem(): MediaItem {
    val metadata = MediaMetadata.Builder()
        .setTitle(title)
        .setArtist(url)
        .build()

    return MediaItem.Builder()
        .setMediaMetadata(metadata)
        .setUri(url)
        .setMediaId(id)
        .build()
}