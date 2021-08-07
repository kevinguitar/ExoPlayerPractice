package com.example.exoplayerpractice.data

/**
 *  https://simpleguics2pygame.readthedocs.io/en/latest/_static/links/snd_links.html
 */

private val freeTracks = listOf(
    Track("track1","Background Music", "http://codeskulptor-demos.commondatastorage.googleapis.com/descent/background%20music.mp3"),
    Track("track2","Ateapill", "http://commondatastorage.googleapis.com/codeskulptor-demos/pyman_assets/ateapill.ogg"),
    Track("track3","The Neverwritten Role Playing Game", "http://commondatastorage.googleapis.com/codeskulptor-demos/DDR_assets/Kangaroo_MusiQue_-_The_Neverwritten_Role_Playing_Game.mp3"),
    Track("track4","Nbsp", "http://commondatastorage.googleapis.com/codeskulptor-demos/DDR_assets/Sevish_-__nbsp_.mp3"),
    Track("track5","Race1", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/race1.ogg"),
    Track("track6","Race2", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/race2.ogg"),
    Track("track7","Paza moduless", "http://codeskulptor-demos.commondatastorage.googleapis.com/pang/paza-moduless.mp3"),
    Track("track8","Menu", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/menu.ogg"),
    Track("track9","Win", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/win.ogg"),
    Track("track10","Lose", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/lose.ogg"),
    Track("track11","Soundtrack", "http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/soundtrack.ogg"),
    Track("track12","Thrust", "http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/thrust.mp3"),
)

val playlists = listOf(
    Playlist("Playlist 1", listOf(freeTracks[0], freeTracks[1], freeTracks[2], freeTracks[3])),
    Playlist("Playlist 2", listOf(freeTracks[4], freeTracks[5], freeTracks[6], freeTracks[7])),
    Playlist("Playlist 3", listOf(freeTracks[8], freeTracks[9], freeTracks[10], freeTracks[11])),
    Playlist("Playlist 4", listOf(freeTracks[0], freeTracks[3], freeTracks[5], freeTracks[8])),
)