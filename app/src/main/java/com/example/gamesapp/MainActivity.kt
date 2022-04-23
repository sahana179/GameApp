package com.example.gamesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var data_name: String = ""
        var data_url: String = ""
        var data_id: Int
        val data : MutableList<GamesObject> = arrayListOf()

        val gameList = findViewById<RecyclerView>(R.id.acivity_list_games)

        val myItemClickListener = View.OnClickListener {
            // we retrieve the row position from its tag
            val position = it.tag as Int
            val clickedItem = data[position]
            // do stuff
            Toast.makeText(this@MainActivity, "Clicked " + clickedItem.name, Toast.LENGTH_SHORT).show()
            val explicitIntent: Intent = Intent(this, SecondPage::class.java)
            explicitIntent.putExtra("MESSAGE1",clickedItem.name)
            explicitIntent.putExtra("MESSAGE2",clickedItem.picture)
            explicitIntent.putExtra("MESSAGE3",clickedItem.id.toString())
            startActivity(explicitIntent)
        }


        val retrofitClient = Retrofit.Builder()
            .baseUrl("https://education.3ie.fr/android/ressources/api/game/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        val service = retrofitClient.create(GamesWSInterface::class.java)

        val callback: Callback<List<GamesObject>> = object : Callback<List<GamesObject>> {
            override fun onResponse(call: Call<List<GamesObject>>, response: Response<List<GamesObject>>)
            {
                if (response.isSuccessful)
                {
                    for (i in 0..response.body()!!.size-1)
                    {
                        data_name = response.body()!![i].name
                        data_url = response.body()!![i].picture
                        data_id = response.body()!![i].id

                        data.add(GamesObject(data_name,data_id, data_url))

                    }

                    gameList.layoutManager = LinearLayoutManager(this@MainActivity)

                    val recyclerAdapter = GamesAdapter(data,this@MainActivity, myItemClickListener)
                    gameList.adapter = recyclerAdapter
                    gameList.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
                }
            }

            override fun onFailure(call: Call<List<GamesObject>>, t: Throwable)
            {
                TODO("Not yet implemented")
            }

        }

        service.getAllJasons().enqueue(callback)
    }

}


