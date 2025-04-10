package com.example.superheroes.activities

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroes.R
import com.example.superheroes.adapters.SuperheroAdapter
import com.example.superheroes.data.Superhero
import com.example.superheroes.utils.SuperheroService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

lateinit var recyclerView: RecyclerView
lateinit var adapter :SuperheroAdapter
var superheroList: List<Superhero> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerview)
        adapter = SuperheroAdapter(superheroList)
        recyclerView.adapter=adapter
        recyclerView.layoutManager = GridLayoutManager(this,2)

        searchSuperheros("a")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu,menu)

        val menuItem = menu.findItem(R.id.menu_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                searchSuperheros(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false            }
        })

        return true
    }


    fun searchSuperheros(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = SuperheroService.getInstance()
                val response = service.findSuperheroesByName(query)
                superheroList = response.results

                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(superheroList)
                }

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}