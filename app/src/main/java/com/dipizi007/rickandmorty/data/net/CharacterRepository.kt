package com.dipizi007.rickandmorty.data.net

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dipizi007.rickandmorty.data.net.entity.Character
import com.dipizi007.rickandmorty.data.net.entity.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface CharacterRepository {

    fun getPageCharacter(): Flow<PagingData<Results>>

    class Base(private val service: CharacterService): CharacterRepository {

        override fun getPageCharacter(): Flow<PagingData<Results>> {
            val data: suspend (page: Int) -> Character = { page ->
                getListCharacter(page)
            }
            return Pager(
                config = PagingConfig(
                    pageSize = 20
                ),
                pagingSourceFactory = { CharacterPagingDataSource(data) }
            ).flow
        }

        private suspend fun getListCharacter(page: Int): Character = withContext(Dispatchers.IO) {
            val character = service.getCharacter(page)
            return@withContext character
        }
    }
}