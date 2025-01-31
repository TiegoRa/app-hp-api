package br.com.hvilar.myapplication

import retrofit2.http.GET
import retrofit2.http.Path

interface StudentApi {
    @GET("character/{id}")
    suspend fun getStudent(@Path("id") id:String): List<Student>
}