package com.dipizi007.rickandmorty.UI.characterFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dipizi007.rickandmorty.R
import com.dipizi007.rickandmorty.databinding.ItemLoadErrorStateBinding
import com.dipizi007.rickandmorty.utils.ResourceProvider

class LoadAndErrorStateAdapter(
    private val retry: () -> Unit,
    private val resourceProvider: ResourceProvider
) : LoadStateAdapter<LoadAndErrorStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(
        holder: ViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLoadErrorStateBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(
        private val binding: ItemLoadErrorStateBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {

            if (loadState is LoadState.Error) {
                binding.tvError.text = resourceProvider.getString(R.string.error_message)
            }

            binding.progressBar.isVisible = (loadState is LoadState.Loading)
            binding.tvError.isVisible = (loadState is LoadState.Error)
            binding.btnRetry.isVisible = (loadState is LoadState.Error)
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}