package com.dipizi007.rickandmorty.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dipizi007.rickandmorty.App
import com.dipizi007.rickandmorty.UI.characterInfoFragment.CharacterInfoViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val app: App): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass) {
            com.dipizi007.rickandmorty.UI.characterFragment.CharacterViewModel::class.java -> {
                com.dipizi007.rickandmorty.UI.characterFragment.CharacterViewModel(app.repository)
            }

            CharacterInfoViewModel::class.java -> {
                CharacterInfoViewModel(app.retrofit)
            }
            else -> {
                throw IllegalArgumentException()
            }
        } as T
    }
}