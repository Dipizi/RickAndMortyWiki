package com.dipizi007.rickandmorty.UI.characterInfoFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dipizi007.rickandmorty.App
import com.dipizi007.rickandmorty.R
import com.dipizi007.rickandmorty.data.net.entity.Result
import com.dipizi007.rickandmorty.databinding.FragmentCharacterInfoBinding
import com.dipizi007.rickandmorty.utils.ViewModelFactory


class CharacterInfoFragment : Fragment() {

    lateinit var viewBinding: FragmentCharacterInfoBinding

    private val viewModel by viewModels<CharacterInfoViewModel> { ViewModelFactory(requireContext().applicationContext as App) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCharacterInfoBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resourceProvider = (requireContext().applicationContext as App).resourceProvider
        val id = arguments?.getInt(CHARACTER_ID)
        getCharacterById(id)

        viewBinding.buttonRetry.setOnClickListener {
            getCharacterById(id)
        }

        viewModel.character.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Pending -> {
                    viewBinding.progressBarCharacter.visibility = View.VISIBLE
                    viewBinding.textviewError.visibility = View.GONE
                    viewBinding.buttonRetry.visibility = View.GONE
                }

                is Result.Error -> {
                    viewBinding.progressBarCharacter.visibility = View.GONE
                    viewBinding.textviewError.visibility = View.VISIBLE
                    viewBinding.buttonRetry.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    with(viewBinding) {
                        linerLayoutResult.visibility = View.GONE
                        series.visibility = View.VISIBLE
                        textViewName.text =
                            resourceProvider.getString(R.string.name, result.data.name)
                        textViewStatus.text =
                            resourceProvider.getString(R.string.status, result.data.status)
                        textViewGender.text =
                            resourceProvider.getString(R.string.gender, result.data.gender)
                        textViewSpecies.text =
                            resourceProvider.getString(R.string.species, result.data.species)
                        Glide
                            .with(imageViewCharacter.context)
                            .load(result.data.image)
                            .centerInside()
                            .into(imageViewCharacter)
                    }
                }
            }
        }

        viewModel.episode.observe(viewLifecycleOwner) {
            viewBinding.listView.adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)
        }
    }

    private fun getCharacterById(id: Int?) {
        if (id != null)
            viewModel.getCharacterById(id)
    }

    private companion object {
        const val CHARACTER_ID = "id"
    }
}