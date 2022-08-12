package com.dipizi007.rickandmorty.UI.characterInfoFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipizi007.rickandmorty.data.net.CharacterService
import com.dipizi007.rickandmorty.data.net.entity.Result
import com.dipizi007.rickandmorty.data.net.entity.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterInfoViewModel(private val service: CharacterService) : ViewModel() {

    private val _character = MutableLiveData<Result<Results>>()
    val character: LiveData<Result<Results>> = _character

    private val _episode = MutableLiveData<List<String>>()
    val episode: LiveData<List<String>> = _episode


    fun getCharacterById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _character.postValue(Result.Pending())
        try {
            val character = service.getCharacterById(id)
            getSeriesByCharacterId(id)
            _character.postValue(Result.Success(character))
        } catch (e: Exception) {
            _character.postValue(Result.Error(e))
        }
    }

    private suspend fun getSeriesByCharacterId(id: Int) {
        val listSeries = mutableListOf<String>()
        val character = service.getCharacterById(id)
        character.episode.forEach { episode ->
            val idEpisode = episode.substringAfterLast("/").toInt()
            val series = service.getEpisodeById(idEpisode)
            listSeries.add("${series.name} - ${series.episode}")
        }
        _episode.postValue(listSeries)
    }
}