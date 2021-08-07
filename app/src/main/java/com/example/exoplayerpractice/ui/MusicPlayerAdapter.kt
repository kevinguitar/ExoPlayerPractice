package com.example.exoplayerpractice.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exoplayerpractice.BR
import com.example.exoplayerpractice.R
import com.example.exoplayerpractice.databinding.ItemPlaylistBinding
import com.example.exoplayerpractice.databinding.ItemTrackBinding
import javax.inject.Inject

private enum class ItemType { Playlist, Track }

class MusicPlayerAdapter @Inject constructor() :
    ListAdapter<Any, MusicPlayerAdapter.BindingViewHolder>(alwaysRedrawDiff) {

    private val types = ItemType.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (types[viewType]) {
            ItemType.Playlist -> DataBindingUtil.inflate<ItemPlaylistBinding>(
                inflater, R.layout.item_playlist, parent, false
            )
            ItemType.Track -> DataBindingUtil.inflate<ItemTrackBinding>(
                inflater, R.layout.item_track, parent, false
            )
        }
        view.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return BindingViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is PlaylistViewModel -> ItemType.Playlist.ordinal
        is TrackViewModel -> ItemType.Track.ordinal
        else -> error("not supported view type")
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BindingViewHolder(private val view: ViewDataBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(model: Any) {
            view.setVariable(BR.model, model)
            view.executePendingBindings()
        }
    }
}

private val alwaysRedrawDiff = object : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = false
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = false
}