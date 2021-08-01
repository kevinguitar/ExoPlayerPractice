package com.example.exoplayerpractice.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exoplayerpractice.BR
import com.example.exoplayerpractice.R
import com.example.exoplayerpractice.databinding.ItemTrackBinding
import javax.inject.Inject

class TracksAdapter @Inject constructor() :
    ListAdapter<TrackViewModel, TracksAdapter.TrackViewHolder>(trackModelDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemTrackBinding>(
            inflater,
            R.layout.item_track,
            parent,
            false
        )
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TrackViewHolder(private val view: ViewDataBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(model: TrackViewModel) {
            view.setVariable(BR.model, model)
            view.executePendingBindings()
        }
    }
}

private val trackModelDiff
    get() = object : DiffUtil.ItemCallback<TrackViewModel>() {
        override fun areItemsTheSame(oldItem: TrackViewModel, newItem: TrackViewModel): Boolean =
            oldItem.track.url == newItem.track.url

        override fun areContentsTheSame(oldItem: TrackViewModel, newItem: TrackViewModel): Boolean =
            oldItem.track == newItem.track
    }