package com.example.exoplayerpractice.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.example.exoplayerpractice.player.ExoMusicPlayer
import com.example.exoplayerpractice.player.MusicPlayer
import com.example.exoplayerpractice.player.MusicPlayerControl
import com.example.exoplayerpractice.player.MusicPlayerState
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PlayerModule {

    @Binds
    fun provideMusicPlayer(impl: ExoMusicPlayer): MusicPlayer

    @Binds
    fun provideMusicPlayerState(impl: ExoMusicPlayer): MusicPlayerState

    @Binds
    fun provideMusicPlayerControl(impl: ExoMusicPlayer): MusicPlayerControl

    companion object {

        @Provides
        @Singleton
        fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer {
            return ExoPlayer.Builder(context).build()
        }
    }
}