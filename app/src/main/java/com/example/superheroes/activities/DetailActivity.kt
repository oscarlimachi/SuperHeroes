package com.example.superheroes.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.superheroes.R
import com.example.superheroes.data.Superhero
import com.example.superheroes.databinding.ActivityDetailBinding
import com.example.superheroes.utils.SuperheroService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object{
        const val SUPERHERO_ID = "SUPERHERO_ID"
    }
    lateinit var binding: ActivityDetailBinding
    lateinit var superhero: Superhero



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra(SUPERHERO_ID)!!
        getSuperheroById(id)

        //invocar a la navegacion y recibir pulsar
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem->
            binding.contentBiography.root.visibility = View.GONE
            binding.contentAppearance.root.visibility = View.GONE
            binding.contentPowerStats.root.visibility = View.GONE

            when(menuItem.itemId){
                R.id.menuBiography -> binding.contentBiography.root.visibility = View.VISIBLE
                R.id.menuAppearance -> binding.contentAppearance.root.visibility = View.VISIBLE
                R.id.menuStats -> binding.contentPowerStats.root.visibility = View.VISIBLE
            }
            true
        }

        /*forzamos para a pulsar click para que se oculte*/
        binding.bottomNavigationView.selectedItemId = R.id.menuBiography
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    fun getSuperheroById(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = SuperheroService.getInstance()
                superhero = service.findSuperheroById(id)
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }


            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    fun loadData(){
        supportActionBar?.title=superhero.name
        supportActionBar?.subtitle=superhero.biography.realName
        Picasso.get().load(superhero.image.url).into(binding.avatarImageView)

        //Biography
        binding.contentBiography.publisherTextView.text = superhero.biography.publisher
        binding.contentBiography.placeOfBirthTextView.text = superhero.biography.placeOfBirth
        binding.contentBiography.alignmentTextView.text = superhero.biography.alignment
        binding.contentBiography.alignmentTextView.setTextColor(getColor(superhero.getAlignmentColor()))
        binding.contentBiography.occupationTextView.text = superhero.work.occupation
        binding.contentBiography.baseTextView.text = superhero.work.base
        //Appearance
        //binding.contentAppearance.raceTextView.text = superhero.getAppearanceRace()
        with(superhero.appearance){
            binding.contentAppearance.genderTextView.text = gender
            binding.contentAppearance.raceTextView.text = superhero.getAppearanceRace()
            binding.contentAppearance.eyeColorTextView.text = eyeColor
            binding.contentAppearance.hairColorTextView.text = hairColor
            binding.contentAppearance.heightTextView.text = height[1]
            binding.contentAppearance.weightTextView.text = weight[1]
        }
        //Stats
        with(superhero.stats){
            binding.contentPowerStats.intelligenceTextView.text = "${intelligence}"
            binding.contentPowerStats.intelligenceProgress.progress = intelligence

            binding.contentPowerStats.strengthTextView.text = "${strength}"
            binding.contentPowerStats.strengthProgress.progress = strength

            binding.contentPowerStats.speedTextView.text = "${speed}"
            binding.contentPowerStats.speedProgress.progress = speed

            binding.contentPowerStats.durabilityTextView.text = "${durability}"
            binding.contentPowerStats.durabilityProgress.progress = durability

            binding.contentPowerStats.powerTextView.text = "${power}"
            binding.contentPowerStats.powerProgress.progress = power

            binding.contentPowerStats.combatTextView.text = "${combat}"
            binding.contentPowerStats.combatProgress.progress = combat

        }

    }



}