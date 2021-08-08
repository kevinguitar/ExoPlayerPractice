package com.example.exoplayerpractice.player

/**
 *  @param current  Current playback position in millisecond.
 *  @param duration Current track duration in millisecond.
 */
data class PlaybackProgress(
    val current: Long,
    val duration: Long
)
