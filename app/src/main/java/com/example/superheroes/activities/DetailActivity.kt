package com.example.superheroes.activities

import android.os.Bundle
import android.view.LayoutInflater
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

        val id = intent.getStringExtra(SUPERHERO_ID)!!

        getSuperheroById(id)

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
        binding.nameTextView.text=superhero.name
        Picasso.get().load(superhero.image.url).into(binding.avatarImageView)
    }



}