package com.example.gamesapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)

        val originIntent = intent
        val message1 = originIntent.getStringExtra("MESSAGE1")
        val message2 = originIntent.getStringExtra("MESSAGE2")
        val message3 = originIntent.getStringExtra("MESSAGE3")

        val second_Name = findViewById<TextView>(R.id.secondP_name)
        val second_Picture = findViewById<ImageView>(R.id.secondP_image)
        val second_Type = findViewById<TextView>(R.id.secondP_type)
        val second_NPlayers = findViewById<TextView>(R.id.secondP_players)
        val second_Year = findViewById<TextView>(R.id.secondP_year)
        val second_Description = findViewById<TextView>(R.id.secondP_desc)
        val second_MoreInfo = findViewById<Button>(R.id.secondP_button)

        var path_name: String = "details" + message3


        second_MoreInfo.setOnClickListener {
            Toast.makeText(this@SecondPage, "Please Google Maadi", Toast.LENGTH_SHORT).show();
        }

        second_MoreInfo.setOnClickListener {
            val url : String = "https://play.google.com/store/apps/details?id=com.google.android.play.games&hl=en&gl=US"
            val implicitIntent : Intent = Intent(Intent.ACTION_VIEW)

            implicitIntent.data = Uri.parse(url)
            startActivity(implicitIntent)
        }

        val retrofitClient = Retrofit.Builder()
            .baseUrl("https://education.3ie.fr/android/ressources/api/game/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        val service = retrofitClient.create(GamesWSInterface::class.java)

        val callback: Callback<GameDescObject> = object : Callback<GameDescObject> {
            override fun onResponse(call: Call<GameDescObject>, response: Response<GameDescObject>)
            {
                if (response.isSuccessful)
                {
                    second_Name.text = response.body()!!.name
                    second_Type.text = response.body()!!.type
                    second_NPlayers.text = response.body()!!.players.toString()
                    second_Year.text = response.body()!!.year.toString()
                    second_Description.text = response.body()!!.description_en
                    Glide.with(this@SecondPage)
                        .load(response.body()!!.picture)
                        .into(second_Picture)
                  //  Picasso.get().load(response.body()!!.picture).into(second_Picture)
                }
            }

            override fun onFailure(call: Call<GameDescObject>, t: Throwable)
            {
                TODO("Not yet implemented")
            }

        }
        service.getAllData(path_name).enqueue(callback)

    }
}