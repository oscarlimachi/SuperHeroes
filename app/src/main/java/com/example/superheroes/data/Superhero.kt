package com.example.superheroes.data

import android.os.WorkSource
import androidx.annotation.ColorRes
import com.example.superheroes.R
import com.google.android.material.resources.TextAppearance
import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter


data class Superhero(
    val id: String,
    val name: String,
    val image: Image,
    val biography: Biography,
    val work: Work,
    val appearance: Appearance,
    @SerializedName("powerstats") val stats: Stats
    ){
    @ColorRes
    fun getAlignmentColor() : Int {
        return when(biography.alignment){
            "good" -> R.color.alignment_color_good
            "bad" -> R.color.alignment_color_bad
            else -> R.color.alignment_color_neutral
        }
    }
    fun getAppearanceRace() : String{
        return when(appearance.race){
            "null" ->"Unknown"
            else -> appearance.race

        }
    }
}


data class Image(
    val url: String
)
data class Biography(
    @SerializedName("full-name") val realName: String,
    @SerializedName("place-of-birth") val placeOfBirth: String,
    val publisher: String,
    val alignment: String
)

data class Work(
    val occupation: String,
    val base: String
)
class Stats(
    @JsonAdapter(IntegerAdapter::class) var intelligence: Int,
    @JsonAdapter(IntegerAdapter::class) var strength: Int,
    @JsonAdapter(IntegerAdapter::class) var speed: Int,
    @JsonAdapter(IntegerAdapter::class) var durability: Int,
    @JsonAdapter(IntegerAdapter::class) var power: Int,
    @JsonAdapter(IntegerAdapter::class) var combat:Int
)

class Appearance(
    val gender: String,
    val race: String,
    @SerializedName("eye-color")  val eyeColor: String,
    @SerializedName("hair-color") val hairColor: String,
    val height: List<String>,
    val weight: List<String>
)
data class SuperheroSearchResponse(
    val results: List<Superhero>
)

class IntegerAdapter : TypeAdapter<Int>() {
    override fun write(out: JsonWriter?, value: Int) {
        out?.value(value)
    }

    override fun read(`in`: JsonReader?): Int {
        return try {
            `in`!!.nextString()!!.toInt()
        } catch (e: Exception) {
            0
        }
    }

}

