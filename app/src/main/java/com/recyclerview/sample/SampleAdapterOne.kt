package com.recyclerview.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recyclerview.sample.model.Profile
import kotlinx.android.synthetic.main.item_layout.view.*

class SampleAdapterOne : ListAdapter<Profile, SampleAdapterOne.SampleOneViewHolder>(ProfileItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleOneViewHolder {
        return SampleOneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: SampleOneViewHolder, position: Int) {
        holder.bind()
    }

    override fun onBindViewHolder(holder: SampleOneViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            when (payloads[0]) {
                Payload.IMAGE -> {
                    holder.bindImage(position)
                }
                Payload.TITLE -> {
                    holder.bindTitle(position)
                }
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    inner class SampleOneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val profile = getItem(position)
                with(itemView) {
                    Glide.with(this).load(profile.image).centerCrop().into(image)
                    title.text = profile.name
                }
            }
        }

        fun bindTitle(position: Int) {
            if (position != RecyclerView.NO_POSITION) {
                val profile = getItem(position)
                with(itemView) {
                    title.text = profile.name
                }
            }
        }

        fun bindImage(position: Int) {
            if (position != RecyclerView.NO_POSITION) {
                val profile = getItem(position)
                with(itemView) {
                    Glide.with(this).load(profile.image).centerCrop().into(image)
                }
            }
        }
    }

    class ProfileItemCallback : DiffUtil.ItemCallback<Profile>() {
        override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Profile, newItem: Profile): Any? {
            return when {
                oldItem.name != newItem.name -> Payload.TITLE
                oldItem.image != newItem.image -> Payload.IMAGE
                else -> null
            }
        }
    }

    enum class Payload {
        TITLE, IMAGE
    }
}