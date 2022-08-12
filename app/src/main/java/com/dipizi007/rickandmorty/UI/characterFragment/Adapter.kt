package com.dipizi007.rickandmorty.UI.characterFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dipizi007.rickandmorty.R
import com.dipizi007.rickandmorty.data.net.entity.Results
import com.dipizi007.rickandmorty.databinding.ItemCharacterBinding
import com.dipizi007.rickandmorty.utils.ResourceProvider

class Adapter(
    private val resourceProvider: ResourceProvider,
    private val listener: onCharacterStateListener
) :
    PagingDataAdapter<Results, Adapter.ViewHolder>(DiffUtilItemCallBack) {

    interface onCharacterStateListener {

        fun onCharacterClickListener(character: Results)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, resourceProvider)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { results ->
            holder.bind(results)
            holder.itemView.setOnClickListener {
                listener.onCharacterClickListener(results)
            }
        }
    }


    class ViewHolder(
        private val binding: ItemCharacterBinding,
        private val resourceProvider: ResourceProvider,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Results) {
            binding.name.text = resourceProvider.getString(R.string.name, character.name)
            binding.tvStatus.text = resourceProvider.getString(R.string.status, character.status)
            Glide
                .with(binding.ivAvatar.context)
                .load(character.image)
                .placeholder(R.drawable.ic_baseline_account_box_24)
                .into(binding.ivAvatar)
            val color = setColorStatus(character.status)
            binding.globalCardView.setCardBackgroundColor(color)
        }

        private fun setColorStatus(status: String): Int {
            return when (status) {
                resourceProvider.getString(R.string.statusAlive) -> {
                    resourceProvider.getColor(R.color.green)
                }
                resourceProvider.getString(R.string.statusDead) -> {
                    resourceProvider.getColor(R.color.red)
                }
                else -> {
                    resourceProvider.getColor(R.color.yellow)
                }
            }
        }
    }
}

object DiffUtilItemCallBack : DiffUtil.ItemCallback<Results>() {
    override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
        return oldItem == newItem
    }
}