package com.dipizi007.rickandmorty.data.net.entity


data class Character(
    val info: Info,
    val results: List<Results>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class Results(
    val id: Int,
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: Origin,
    val episode: List<String>
)

data class Origin(
    val name: String,
    val url: String
)

data class Episode(
    val name: String,
    val episode: String
)
