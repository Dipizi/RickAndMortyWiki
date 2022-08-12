package com.dipizi007.rickandmorty

import android.app.Application
import com.dipizi007.rickandmorty.data.net.CharacterRepository
import com.dipizi007.rickandmorty.data.net.CharacterService
import com.dipizi007.rickandmorty.utils.ResourceProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofit = retrofitBuilder.create(CharacterService::class.java)

    val resourceProvider = ResourceProvider(this)

    val repository = CharacterRepository.Base(retrofit)
}