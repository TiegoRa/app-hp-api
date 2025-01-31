package br.com.hvilar.myapplication

import retrofit2.http.GET
import retrofit2.http.Path

interface StaffApi {
    @GET("characters/staff")
    suspend fun getStaff(): List<Student>
}