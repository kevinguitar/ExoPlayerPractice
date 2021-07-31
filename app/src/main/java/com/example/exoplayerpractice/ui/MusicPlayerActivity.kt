package com.example.exoplayerpractice.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.exoplayerpractice.R
import com.example.exoplayerpractice.databinding.AcMusicPlayerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicPlayerActivity : AppCompatActivity() {

    private val viewModel by viewModels<MusicPlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<AcMusicPlayerBinding>(this, R.layout.ac_music_player)
            .apply {
                model = viewModel
                lifecycleOwner = this@MusicPlayerActivity
            }
    }
}