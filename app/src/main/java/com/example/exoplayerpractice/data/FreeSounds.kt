package com.example.exoplayerpractice.data

/**
 *  https://simpleguics2pygame.readthedocs.io/en/latest/_static/links/snd_links.html
 */

private val freeTracks = listOf(
    Track("track1","01. Background Music", "http://codeskulptor-demos.commondatastorage.googleapis.com/descent/background%20music.mp3", "https://i.pinimg.com/originals/4e/b4/f8/4eb4f8a7e04b57e74914fc46e013ac40.jpg"),
    Track("track2","02. Ateapill", "http://commondatastorage.googleapis.com/codeskulptor-demos/pyman_assets/ateapill.ogg", "https://i.pinimg.com/originals/ea/1f/64/ea1f64668a0af149a3277db9e9e54824.jpg"),
    Track("track3","03. The Neverwritten Role Playing Game", "http://commondatastorage.googleapis.com/codeskulptor-demos/DDR_assets/Kangaroo_MusiQue_-_The_Neverwritten_Role_Playing_Game.mp3", "https://thumbs.dreamstime.com/b/dynamic-radial-color-sound-equalizer-design-music-album-cover-template-abstract-circular-digital-data-form-vector-160916775.jpg"),
    Track("track4","04. Nbsp", "http://commondatastorage.googleapis.com/codeskulptor-demos/DDR_assets/Sevish_-__nbsp_.mp3", "https://i.insider.com/5a85ff4dd0307223008b46ed?width=600&format=jpeg&auto=webp"),
    Track("track5","05. Race1", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/race1.ogg", "https://graphicriver.img.customer.envatousercontent.com/files/264102893/brainwash-albumcover-template-preview.jpg?auto=compress%2Cformat&fit=crop&crop=top&w=590&h=590&s=5201f83eeedc67483d4e2a14dea00ee3"),
    Track("track6","06. Race2", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/race2.ogg", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUcm793bw3xYCfI4_ZKJ1qde9tUd-uRhiCIg&usqp=CAU"),
    Track("track7","07. Paza moduless", "http://codeskulptor-demos.commondatastorage.googleapis.com/pang/paza-moduless.mp3", "https://laughingsquid.com/wp-content/uploads/mjbad.jpg"),
    Track("track8","08. Menu", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/menu.ogg", "https://media.karousell.com/media/photos/products/2021/3/23/music_empire_miley_cyrus__plas_1616511963_186749d9_progressive.jpg"),
    Track("track9","09. Win", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/win.ogg", "https://media.karousell.com/media/photos/products/2014/04/04/2pm_dont_stop_cant_stop_music_album_kpop_jyp_3rd_single_album_1396549850_362d6114.jpg"),
    Track("track10","10. Lose", "http://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/music/lose.ogg", "https://i.pinimg.com/originals/3a/f0/e5/3af0e55ea66ea69e35145fb108b4a636.jpg"),
    Track("track11","11. Soundtrack", "http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/soundtrack.ogg", "https://mediamodifier.com/blog/wp-content/uploads/2021/02/Mediamodifier-Design-Template-86-1024x1024.jpg"),
    Track("track12","12. Thrust", "http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/thrust.mp3", "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/193fe961733399.5a7a9fdab3f3c.jpg"),
)

val playlists = listOf(
    Playlist("Playlist 1", listOf(freeTracks[0], freeTracks[1], freeTracks[2], freeTracks[3])),
    Playlist("Playlist 2", listOf(freeTracks[4], freeTracks[5], freeTracks[6], freeTracks[7])),
    Playlist("Playlist 3", listOf(freeTracks[8], freeTracks[9], freeTracks[10], freeTracks[11])),
    Playlist("Playlist 4", listOf(freeTracks[0], freeTracks[3], freeTracks[5], freeTracks[8])),
)