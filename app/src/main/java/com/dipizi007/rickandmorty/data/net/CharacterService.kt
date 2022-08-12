package com.dipizi007.rickandmorty.data.net

import com.dipizi007.rickandmorty.data.net.entity.Character
import com.dipizi007.rickandmorty.data.net.entity.Episode
import com.dipizi007.rickandmorty.data.net.entity.Results
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getCharacter(@Query("page") page: Int): Character

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Results

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): Episode
}