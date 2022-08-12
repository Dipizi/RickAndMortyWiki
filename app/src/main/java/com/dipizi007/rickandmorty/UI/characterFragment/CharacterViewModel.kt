package com.dipizi007.rickandmorty.UI.characterFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dipizi007.rickandmorty.data.net.CharacterRepository
import com.dipizi007.rickandmorty.data.net.entity.Results
import kotlinx.coroutines.flow.Flow

class CharacterViewModel(repository: CharacterRepository) : ViewModel() {

    val characterFlow: Flow<PagingData<Results>> = repository.getPageCharacter().cachedIn(viewModelScope)
}