package com.example.exoplayerpractice.ui

import androidx.lifecycle.ViewModel
import com.example.exoplayerpractice.player.MusicPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val musicPlayer: MusicPlayer
) : ViewModel() {


}