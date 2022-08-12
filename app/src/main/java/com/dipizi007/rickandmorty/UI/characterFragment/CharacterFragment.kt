package com.dipizi007.rickandmorty.UI.characterFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dipizi007.rickandmorty.App
import com.dipizi007.rickandmorty.R
import com.dipizi007.rickandmorty.data.net.entity.Results
import com.dipizi007.rickandmorty.databinding.FragmentCharacterBinding
import com.dipizi007.rickandmorty.utils.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterFragment : Fragment() {

    private lateinit var viewBinding: FragmentCharacterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCharacterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterViewModel: CharacterViewModel by viewModels { ViewModelFactory(requireContext().applicationContext as App) }
        val resourceProvider = (requireContext().applicationContext as App).resourceProvider

        val adapter = Adapter(
            resourceProvider = resourceProvider,
            listener = object : Adapter.onCharacterStateListener {
                override fun onCharacterClickListener(character: Results) {
                    findNavController().navigate(R.id.action_characterFragment_to_characterInfoFragment, bundleOf(
                        CHARACTER_ID to character.id,
                    ))
                }
            }
        )

        val linerLayout = LinearLayoutManager(context)

        viewBinding.buttonError.setOnClickListener {
            adapter.retry()
        }

        viewBinding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            footer = LoadAndErrorStateAdapter(
                resourceProvider = resourceProvider,
                retry = { adapter.retry() }),
            header = LoadAndErrorStateAdapter(
                resourceProvider = resourceProvider,
                retry = { adapter.retry() })
        )
        viewBinding.recyclerView.layoutManager = linerLayout

        viewLifecycleOwner.lifecycleScope.launch {
            characterViewModel.characterFlow.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                viewBinding.linerLayout.isVisible = it.refresh is LoadState.Error
                viewBinding.progressBar2.isVisible = it.refresh is LoadState.Loading
            }
        }

    }

    private companion object {
        const val CHARACTER_ID = "id"
    }
}