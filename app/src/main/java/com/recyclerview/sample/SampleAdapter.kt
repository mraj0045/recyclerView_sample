package com.recyclerview.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recyclerview.sample.model.Profile
import kotlinx.android.synthetic.main.item_layout.view.*

class SampleAdapter(private val mutableList: MutableList<Profile>) : RecyclerView.Adapter<SampleAdapter.SampleHolder>() {

    override fun getItemCount(): Int {
        return mutableList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleHolder {
        return SampleHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: SampleHolder, position: Int) {
        holder.bind()
    }

    override fun onBindViewHolder(holder: SampleHolder, position: Int, payloads: MutableList<Any>) {
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

    fun add(profile: Profile?) {
        profile ?: return
        mutableList.add(profile)
        notifyItemInserted(mutableList.size - 1)
    }

    fun remove(profile: Profile?) {
        profile ?: return
        val indexOf = mutableList.indexOf(profile)
        if (indexOf != -1) {
            mutableList.removeAt(indexOf)
            notifyItemRemoved(indexOf)
        }
    }

    fun update(profile: Profile?) {
        profile ?: return
        val newList = mutableListOf<Profile>()
        newList.addAll(mutableList)
        val indexOf = newList.indexOfFirst { it.id == profile.id }
        if (indexOf != -1) {
            newList[indexOf] = profile
            val result = DiffUtil.calculateDiff(SampleDiffCallBack(mutableList, newList))
            result.dispatchUpdatesTo(this)
        }
    }

    inner class SampleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val profile = mutableList[position]
                with(itemView) {
                    Glide.with(this).load(profile.image).centerCrop().into(image)
                    title.text = profile.name
                }
            }
        }

        fun bindTitle(position: Int) {
            if (position != RecyclerView.NO_POSITION) {
                val profile = mutableList[position]
                with(itemView) {
                    title.text = profile.name
                }
            }
        }

        fun bindImage(position: Int) {
            if (position != RecyclerView.NO_POSITION) {
                val profile = mutableList[position]
                with(itemView) {
                    Glide.with(this).load(profile.image).centerCrop().into(image)
                }
            }
        }
    }

    class SampleDiff : DiffUtil.ItemCallback<Profile>() {
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

    class SampleDiffCallBack(
        private val oldList: MutableList<Profile>,
        private val newList: MutableList<Profile>
    ) :
        DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
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