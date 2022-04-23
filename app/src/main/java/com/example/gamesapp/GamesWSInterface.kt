package com.example.gamesapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesWSInterface {
    @GET("list.json")
    fun getAllJasons(): retrofit2.Call<List<GamesObject>>

    @GET("{jsonName}.json")
//fun getAllData(@Path("jsonName") jsonName: String) : Call<List<GameDiscriptionClass>>
    fun getAllData(@Path("jsonName") jsonName: String) : Call<GameDescObject>

}

