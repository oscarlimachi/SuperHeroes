package com.example.superheroes.data

import android.media.Image
import java.net.URL


data class Superhero(
    val id: String,
    val name: String,
    val image: Image)
data class Image(
    val url: URL
)
data class SuperheroSearchResponse(
    val results: List<Superhero>
)

