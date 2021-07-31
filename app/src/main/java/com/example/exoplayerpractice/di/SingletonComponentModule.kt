package com.example.exoplayerpractice.di

import android.content.Context
import com.example.exoplayerpractice.player.ExoMusicPlayer
import com.example.exoplayerpractice.player.MusicPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonComponentModule {

    @Provides
    @Singleton
    fun provideSimpleExoPlayer(@ApplicationContext context: Context): SimpleExoPlayer {
        return SimpleExoPlayer.Builder(context).build()
    }

    @Provides
    fun provideMusicPlayer(impl: ExoMusicPlayer): MusicPlayer = impl
}