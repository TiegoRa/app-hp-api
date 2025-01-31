package br.com.hvilar.myapplication

import retrofit2.http.GET
import retrofit2.http.Path

interface HouseApi {
    @GET("characters/house/{house}")
    suspend fun getHouseMembers(@Path("house") house:String): List<Student>
}