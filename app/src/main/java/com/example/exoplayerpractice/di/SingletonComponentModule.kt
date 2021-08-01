package com.example.exoplayerpractice.di

import android.content.Context
import com.example.exoplayerpractice.player.ExoMusicPlayer
import com.example.exoplayerpractice.player.MusicPlayer
import com.example.exoplayerpractice.player.MusicPlayerControl
import com.example.exoplayerpractice.player.MusicPlayerState
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SingletonComponentModule {

    @Binds
    fun provideMusicPlayer(impl: ExoMusicPlayer): MusicPlayer

    @Binds
    fun provideMusicPlayerState(impl: ExoMusicPlayer): MusicPlayerState

    @Binds
    fun provideMusicPlayerControl(impl: ExoMusicPlayer): MusicPlayerControl

    companion object {

        @Provides
        @Singleton
        fun provideSimpleExoPlayer(@ApplicationContext context: Context): SimpleExoPlayer {
            return SimpleExoPlayer.Builder(context).build()
        }
    }
}