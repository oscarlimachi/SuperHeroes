package com.example.superheroes.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.superheroes.R
import com.example.superheroes.data.Superhero
import com.example.superheroes.utils.SuperheroService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object{
        val SUPERHERO_ID = "SUPERHERO_ID"
    }
    lateinit var nameTextView: TextView
    lateinit var avatarImageView: ImageView
    lateinit var superhero: Superhero



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nameTextView=findViewById(R.id.nameTextView)
        avatarImageView=findViewById(R.id.avatarImageView)

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
        nameTextView.text=superhero.name
        Picasso.get().load(superhero.image.url).into(avatarImageView)
    }



}