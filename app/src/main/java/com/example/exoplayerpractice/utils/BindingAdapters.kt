package com.example.exoplayerpractice.utils

import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("isActivated")
fun View.setIsActivated(isActivated: Boolean?) {
    isActivated ?: return
    this.isActivated = isActivated
}

@BindingAdapter("srcCompat")
fun ImageView.setImageRes(@DrawableRes drawableRes: Int) {
    if (drawableRes == 0) {
        setImageDrawable(null)
    } else {
        setImageResource(drawableRes)
    }
}

@BindingAdapter("onSeekListener")
fun SeekBar.setOnSeekListener(listener: SeekBar.OnSeekBarChangeListener?) {
    setOnSeekBarChangeListener(listener)
}