package com.example.exoplayerpractice.data

import com.example.exoplayerpractice.player.Playlist

/**
 *  https://simpleguics2pygame.readthedocs.io/en/latest/_static/links/snd_links.html
 */

val freeTracks: Playlist
    get() = listOf(
        Track("Background Music", "http://codeskulptor-demos.commondatastorage.googleapis.com/descent/background%20music.mp3"),
        Track("Ateapill", "http://commondatastorage.googleapis.com/codeskulptor-demos/pyman_assets/ateapill.ogg"),
        Track("The Neverwritten Role Playing Game", "http://commondatastorage.googleapis.com/codeskulptor-demos/DDR_assets/Kangaroo_MusiQue_-_The_Neverwritten_Role_Playing_Game.mp3"),
        Track("Nbsp", "http://commondatastorage.googleapis.com/codeskulptor-demos/DDR_assets/Sevish_-__nbsp_.mp3"),
        Track("Race1", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/race1.ogg"),
        Track("Race2", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/race2.ogg"),
        Track("Paza moduless", "http://codeskulptor-demos.commondatastorage.googleapis.com/pang/paza-moduless.mp3"),
        Track("Menu", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/menu.ogg"),
        Track("Win", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/win.ogg"),
        Track("Lose", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/lose.ogg"),
        Track("Soundtrack", "http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/soundtrack.ogg"),
        Track("Thrust", "http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/thrust.mp3"),
    )