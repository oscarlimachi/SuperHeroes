package com.example.superheroes.data


data class Superhero(
    val id: String,
    val name: String,
    val image: Image)
data class Image(
    val url: String
)
data class SuperheroSearchResponse(
    val results: List<Superhero>
)

