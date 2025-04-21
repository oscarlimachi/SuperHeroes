package com.example.superheroes.data

import com.google.gson.annotations.SerializedName


data class Superhero(
    val id: String,
    val name: String,
    val image: Image,
    val biography: Biography,
    @SerializedName("powerstats") val stats: Stats
    )


data class Image(
    val url: String
)
data class Biography(
    @SerializedName("full-name") val realName: String,
    @SerializedName("place-of-birth") val placeOfBirth: String,
    val publisher: String,
    val alignment: String
)

class Stats(
    var intelligence: String,
    var strength: String,
    var speed: String,
    var durability: String,
    var power: String,
    var combat:String

)
data class SuperheroSearchResponse(
    val results: List<Superhero>
)

